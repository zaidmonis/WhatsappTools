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

public class ItemTwoFragment extends Fragment {
    GridView gv;
    ArrayList<File> list;
    ImageView iv;

    public static ItemTwoFragment newInstance() {
        ItemTwoFragment fragment = new ItemTwoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_item_two, container, false);

        File fil = new File(Environment.getExternalStorageDirectory(), "Whatsapp/Media/.Statuses");
        list = imageReader(fil);


        gv = (GridView) myView.findViewById(R.id.gridView);
        gv.setAdapter(new GridAdapter(getActivity()));
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //Toast.makeText(MainActivity.this, "Hello" + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ImageActivity.class);
                intent.putExtra("path", list.get(position).toString());
                startActivity(intent);
            }
        });

        return myView;
    }
    class GridAdapter extends BaseAdapter {

        private Context ctx;

        public GridAdapter(Context c)
        {
            ctx=c;
        }
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.single_grid, parent, false);
            iv = convertView.findViewById(R.id.imageView);
            String path = getItem(position).toString();
            Glide.with(getActivity()).load(new File(path)).into(iv);
            return convertView;
        }
    }
    ArrayList<File> imageReader(File root){
        ArrayList<File> a = new ArrayList<>();
        File[] files = root.listFiles();
        for(int i = 0 ; i<files.length ; i++){
            if (files[i].isDirectory()){
                a.addAll(imageReader(files[i]));
                Toast.makeText(getActivity(), "Here!", Toast.LENGTH_SHORT).show();
            }
            else{
                if (files[i].getName().endsWith(".jpg") && files[i].length()>1000){
                    a.add(files[i]);
                }
            }
        }
        return a;
    }



}