package com.example.httpinfo.adapter;

import android.content.Context;

import com.example.httpinfo.R;
import com.google.gson.Gson;
import com.yh.network.tools.response.DiagnoseResponse;


/**
 * Created by gunaonian on 2018/3/30.
 */

public class DiagnoseAdapter extends BaseRecyclerAdapter<DiagnoseResponse> {

    public DiagnoseAdapter(Context context, int itemLayoutId) {
        super(context, itemLayoutId);
    }

    @Override
    public void insertData(DiagnoseResponse item) {
        list.clear();
        list.add(item);
        notifyDataSetChanged();
    }

    @Override
    public void convert(BaseRecyclerHolder baseRecyclerHolder, DiagnoseResponse item, int position) {
//        baseRecyclerHolder.setText(R.id.item_activity_result_rv_tv, (position + 1) );
        baseRecyclerHolder.setText(R.id.item_activity_result_rv_tv_param, formatString(new Gson().toJson(item)));
    }

    public static String formatString(String text) {
        StringBuilder json = new StringBuilder();
        String indentString = "";

        for (int i = 0; i < text.length(); i++) {
            char letter = text.charAt(i);
            switch (letter) {
                case '{':
                case '[':
                    json.append(indentString + letter + "\n");
                    indentString = indentString + "\t";
                    json.append(indentString);
                    break;
                case '}':
                case ']':
                    indentString = indentString.replaceFirst("\t", "");
                    json.append("\n" + indentString + letter);
                    break;
                case ',':
                    json.append(letter + "\n" + indentString);
                    break;

                default:
                    json.append(letter);
                    break;
            }
        }

        return json.toString();
    }
}
