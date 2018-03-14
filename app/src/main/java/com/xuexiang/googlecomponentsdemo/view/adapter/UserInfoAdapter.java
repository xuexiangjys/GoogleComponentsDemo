/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xuexiang.googlecomponentsdemo.view.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.xuexiang.googlecomponentsdemo.R;
import com.xuexiang.googlecomponentsdemo.databinding.AdapterUserInfoListItemBinding;
import com.xuexiang.googlecomponentsdemo.db.entity.UserInfoEntity;
import com.xuexiang.googlecomponentsdemo.model.OnItemClickListener;

import java.util.List;
import java.util.Objects;

public class UserInfoAdapter extends RecyclerView.Adapter<UserInfoAdapter.UserInfoViewHolder> {

    List<? extends UserInfoEntity> mUserInfoList;

    @Nullable
    private final OnItemClickListener<UserInfoEntity> mOnItemClickListener;

    public UserInfoAdapter(@Nullable OnItemClickListener<UserInfoEntity> listener) {
        mOnItemClickListener = listener;
    }

    public void setUserInfoList(final List<? extends UserInfoEntity> userInfoList) {
        if (mUserInfoList == null) {
            mUserInfoList = userInfoList;
            notifyItemRangeInserted(0, userInfoList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mUserInfoList.size();
                }

                @Override
                public int getNewListSize() {
                    return userInfoList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mUserInfoList.get(oldItemPosition).getId() ==
                            userInfoList.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    UserInfoEntity newUserInfo = userInfoList.get(newItemPosition);
                    UserInfoEntity oldUserInfo = mUserInfoList.get(oldItemPosition);
                    return newUserInfo.getId() == oldUserInfo.getId()
                            && Objects.equals(newUserInfo.getLoginName(), oldUserInfo.getLoginName())
                            && Objects.equals(newUserInfo.getLoginPassword(), oldUserInfo.getLoginPassword())
                            && Objects.equals(newUserInfo.getAlias(), oldUserInfo.getAlias())
                            && Objects.equals(newUserInfo.getGender(), oldUserInfo.getGender())
                            && Objects.equals(newUserInfo.getSignature(), oldUserInfo.getSignature())
                            && Objects.equals(newUserInfo.getBirthDay(), oldUserInfo.getBirthDay())
                            && newUserInfo.getAge() == oldUserInfo.getAge();
                }
            });
            mUserInfoList = userInfoList;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public UserInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        AdapterUserInfoListItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.adapter_user_info_list_item,
                        parent, false);
        binding.setOnItemClickListener(mOnItemClickListener);
        return new UserInfoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(UserInfoViewHolder holder, int position) {
        holder.binding.setUserInfo(mUserInfoList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mUserInfoList == null ? 0 : mUserInfoList.size();
    }

    static class UserInfoViewHolder extends RecyclerView.ViewHolder {

        final AdapterUserInfoListItemBinding binding;

        public UserInfoViewHolder(AdapterUserInfoListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
