package zaidstudios.whatsapptools;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

import zaidstudios.rawderm.whatsapptools.R;

public class ItemThreeFragment extends Fragment {
    GridView gv2;
    ArrayList<File> list2;
    ImageView iv2;

    public static ItemThreeFragment newInstance() {
        ItemThreeFragment fragment = new ItemThreeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView2 = inflater.inflate(R.layout.fragment_item_three, container, false);
        getActivity().setTitle("Video Statuses");

        File fil2 = new File(Environment.getExternalStorageDirectory(), "Whatsapp/Media/.Statuses");
        list2 = imageReader2(fil2);


        gv2 = (GridView) myView2.findViewById(R.id.gridView2);
        gv2.setAdapter(new GridAdapter2(getActivity()));
        gv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //Toast.makeText(MainActivity.this, "Hello" + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), VideoActivity.class);
                intent.putExtra("path2", list2.get(position).toString());
                //Toast.makeText(getActivity(), position, Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        return myView2;
    }
    class GridAdapter2 extends BaseAdapter {

        private Context ctx;

        public GridAdapter2(Context c)
        {
            ctx=c;
        }
        @Override
        public int getCount() {
            return list2.size();
        }

        @Override
        public Object getItem(int position) {
            return list2.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.single_grid2, parent, false);
            iv2 = convertView.findViewById(R.id.imageView2);
            String path = getItem(position).toString();
            Glide.with(getActivity()).load(new File(path)).into(iv2);


            return convertView;
        }
    }
    ArrayList<File> imageReader2(File root){
        ArrayList<File> a = new ArrayList<>();
        File[] files = root.listFiles();
        for(int i = 0 ; i<files.length ; i++){
            if (files[i].isDirectory()){
                a.addAll(imageReader2(files[i]));
            }
            else{
                if (files[i].getName().endsWith(".mp4") && files[i].length()>1000){
                    a.add(files[i]);
                }
            }
        }
        return a;
    }



}