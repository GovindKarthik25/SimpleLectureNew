package com.simplelecture.main.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import com.simplelecture.main.R;
import com.simplelecture.main.model.viewmodel.SelectMyCourseResponseModel;

import java.util.List;

/**
 * Created by Raos on 6/25/2016.
 */
public class SelectYourCourseAdapter extends BaseAdapter {
    private final Activity activity;
    private final List<SelectMyCourseResponseModel> selectMyCourseLstArray;
    private ViewHolder holder;

    public SelectYourCourseAdapter(Activity selectYourCoursesActivity, List<SelectMyCourseResponseModel> selectMyCourseLstAray) {
        this.activity = selectYourCoursesActivity;
        this.selectMyCourseLstArray = selectMyCourseLstAray;

    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return this.selectMyCourseLstArray.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return selectMyCourseLstArray.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        holder = new ViewHolder();

        SelectMyCourseResponseModel selectMyCourseResponseModelObj = selectMyCourseLstArray.get(position);


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.adapter_selectmycourse_listitem, null);
            holder.chkListItem = (CheckBox) convertView.findViewById(R.id.chkListItem);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }



        holder.chkListItem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View checkboxView) {
                SelectMyCourseResponseModel addrCB = (SelectMyCourseResponseModel) checkboxView.getTag();

                // Single selection
                for (int i = 0; i < selectMyCourseLstArray.size(); i++) {
                    selectMyCourseLstArray.get(i).setSelected(false);
                }

                addrCB.setSelected(true);

                notifyDataSetChanged();
            }
        });

        holder.chkListItem.setTag(selectMyCourseResponseModelObj);
        holder.chkListItem.setText(selectMyCourseResponseModelObj.getName());

        // Single selection
           /* if (holder.chkListItem.isChecked() && Util.getFromPrefrences(activity, "SelectYourCategoryID").equalsIgnoreCase(selectMyCourseResponseModelObj.getId())) {
                selectMyCourseResponseModelObj.setSelected(true);
                // holder.chkListItem.setChecked();
            }
*/

            holder.chkListItem.setChecked(selectMyCourseResponseModelObj.isSelected());


        return convertView;
    }


    static class ViewHolder{
        protected CheckBox chkListItem;
    }
}
