/*
 * Copyright 2015-2016 Nickolay Savchenko
 * Copyright 2017-2019 Nikita Shakarun
 * Copyright 2019-2023 Yury Kharchenko
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.playsoftware.j2meloader.applist;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.playsoftware.j2meloader.R;
import ru.playsoftware.j2meloader.config.Config;
import ru.playsoftware.j2meloader.databinding.ListRowGridJarBinding;
import ru.playsoftware.j2meloader.databinding.ListRowJarBinding;

class AppsListAdapter extends ListAdapter<AppItem, AppsListAdapter.AppViewHolder> implements Filterable {
	static final int LAYOUT_TYPE_LIST = 0;
	static final int LAYOUT_TYPE_GRID = 1;
	static final int TAB_ALL = 0;
	static final int TAB_FAVORITES = 1;
	static final int TAB_RECENT = 2;
	private static final int RECENT_LIMIT = 30;

	interface OnLaunchListener {
		void onLaunch(AppItem item);
	}

	private final View.OnCreateContextMenuListener contextMenuListener;
	private List<AppItem> list = new ArrayList<>();
	private final AppFilter appFilter = new AppFilter();
	private CharSequence filterConstraint;
	private View emptyView;
	private int layout = LAYOUT_TYPE_LIST;
	private volatile int tab = TAB_ALL;
	private OnLaunchListener launchListener;

	AppsListAdapter(View.OnCreateContextMenuListener contextMenuListener) {
		super(new DiffUtil.ItemCallback<AppItem>() {
			@Override
			public boolean areItemsTheSame(@NonNull AppItem oldItem, @NonNull AppItem newItem) {
				return oldItem.getId() == newItem.getId();
			}

			@Override
			public boolean areContentsTheSame(@NonNull AppItem oldItem, @NonNull AppItem newItem) {
				return oldItem.getTitle().equals(newItem.getTitle()) &&
						oldItem.getVersion().equals(newItem.getVersion()) &&
						oldItem.isFavorite() == newItem.isFavorite() &&
						oldItem.getLastPlayed() == newItem.getLastPlayed();
			}
		});
		this.contextMenuListener = contextMenuListener;
	}

	@Override
	public int getItemViewType(int position) {
		return layout;
	}

	@NonNull
	@Override
	public AppViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		if (viewType == 1) {
			ListRowGridJarBinding binding = ListRowGridJarBinding.inflate(inflater, parent, false);
			return new AppViewHolder(binding, contextMenuListener);
		} else {
			ListRowJarBinding binding = ListRowJarBinding.inflate(inflater, parent, false);
			return new AppListViewHolder(binding, contextMenuListener);
		}
	}

	@Override
	public void onBindViewHolder(@NonNull AppViewHolder holder, int position) {
		holder.onBind(getItem(position));
	}

	void setItems(List<AppItem> items) {
		list = items;
		appFilter.filter(filterConstraint);
	}

	@Override
	public Filter getFilter() {
		return appFilter;
	}

	void setLaunchListener(OnLaunchListener listener) {
		this.launchListener = listener;
	}

	void setTab(int tab) {
		this.tab = tab;
		appFilter.filter(filterConstraint);
	}

	void setEmptyView(View emptyView) {
		this.emptyView = emptyView;
	}

	void setLayout(int layout) {
		this.layout = layout;
	}

	static class AppViewHolder extends RecyclerView.ViewHolder {
		private final ImageView icon;
		private final TextView name;

		AppViewHolder(ListRowGridJarBinding binding, View.OnCreateContextMenuListener contextMenuListener) {
			this(binding.getRoot(), binding.listTitle, binding.listImage, contextMenuListener);
		}

		AppViewHolder(View itemView,
					  TextView listTitle,
					  ImageView listImage,
					  View.OnCreateContextMenuListener contextMenuListener) {
			super(itemView);
			icon = listImage;
			name = listTitle;

			itemView.setOnClickListener(v -> {
				AppsListAdapter adapter = (AppsListAdapter) getBindingAdapter();
				if (adapter != null) {
					AppItem item = adapter.getItem(getLayoutPosition());
					if (adapter.launchListener != null) {
						adapter.launchListener.onLaunch(item);
					}
					Config.startApp(v.getContext(), item.getTitle(), item.getPathExt(), false);
				}
			});
			itemView.setOnCreateContextMenuListener(contextMenuListener);
		}

		void onBind(AppItem item) {
			Drawable icon = Drawable.createFromPath(item.getImagePathExt());
			if (icon != null) {
				icon.setFilterBitmap(false);
				this.icon.setImageDrawable(icon);
			} else {
				this.icon.setImageResource(R.mipmap.ic_launcher);
			}
			name.setText(item.isFavorite() ? "\u2605 " + item.getTitle() : item.getTitle());
			itemView.setTag(item);
		}
	}

	static class AppListViewHolder extends AppViewHolder {
		private final TextView author;
		private final TextView version;

		AppListViewHolder(ListRowJarBinding binding, View.OnCreateContextMenuListener contextMenuListener) {
			super(binding.getRoot(), binding.listTitle, binding.listImage, contextMenuListener);
			author = binding.listAuthor;
			version = binding.listVersion;
		}

		@Override
		void onBind(AppItem item) {
			super.onBind(item);
			author.setText(item.getAuthor());
			version.setText(item.getVersion());
		}
	}

	class AppFilter extends Filter {

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			FilterResults results = new FilterResults();
			List<AppItem> base = list;
			int currentTab = tab;
			if (currentTab == TAB_FAVORITES) {
				base = new ArrayList<>();
				for (AppItem item : list) {
					if (item.isFavorite()) {
						base.add(item);
					}
				}
			} else if (currentTab == TAB_RECENT) {
				base = new ArrayList<>();
				for (AppItem item : list) {
					if (item.getLastPlayed() > 0) {
						base.add(item);
					}
				}
				Collections.sort(base, (a, b) -> Long.compare(b.getLastPlayed(), a.getLastPlayed()));
				if (base.size() > RECENT_LIMIT) {
					base = new ArrayList<>(base.subList(0, RECENT_LIMIT));
				}
			}
			if (isEmpty(constraint)) {
				results.count = base.size();
				results.values = base;
			} else {
				ArrayList<AppItem> resultList = new ArrayList<>();
				for (AppItem item : base) {
					if (item.getTitle().toLowerCase().contains(constraint) ||
							item.getAuthor().toLowerCase().contains(constraint)) {
						resultList.add(item);
					}
				}
				results.count = resultList.size();
				results.values = resultList;
			}
			return results;
		}

		boolean isEmpty(CharSequence constraint) {
			return constraint == null || constraint.toString().trim().length() == 0;
		}

		@Override
		protected void publishResults(CharSequence constraint, FilterResults results) {
			filterConstraint = constraint;
			//noinspection unchecked
			submitList((List<AppItem>) results.values);
			if (results.count > 0) {
				emptyView.setVisibility(View.GONE);
			} else {
				if (list.isEmpty()) {
					((TextView) emptyView).setText(R.string.no_data_for_display);
				} else if (tab == TAB_FAVORITES && isEmpty(constraint)) {
					((TextView) emptyView).setText(R.string.tab_empty_favorites);
				} else if (tab == TAB_RECENT && isEmpty(constraint)) {
					((TextView) emptyView).setText(R.string.tab_empty_recent);
				} else {
					String msg = emptyView.getResources().getString(R.string.msg_no_matches, constraint);
					((TextView) emptyView).setText(msg);
				}
				emptyView.setVisibility(View.VISIBLE);
			}
		}
	}
}
