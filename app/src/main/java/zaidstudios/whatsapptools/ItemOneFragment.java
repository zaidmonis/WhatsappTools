package zaidstudios.whatsapptools;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mukesh.countrypicker.Country;
import com.mukesh.countrypicker.CountryPicker;
import com.mukesh.countrypicker.OnCountryPickerListener;

import static android.media.tv.TvContract.Programs.Genres.encode;

public class ItemOneFragment extends Fragment {
    private EditText editno, editmsg;
    private Button button, codeButton;
    private String url, msg;
    private View myView;

    public static ItemOneFragment newInstance() {
        ItemOneFragment fragment = new ItemOneFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_item_one, container, false);
        editno = myView.findViewById(R.id.editno);
        editmsg = myView.findViewById(R.id.editmsg);
        button = (Button)myView.findViewById(R.id.button);
        codeButton =(Button) myView.findViewById(R.id.codeButton);
        getActivity().setTitle("Open in Whatsapp");

        codeButton.setBackgroundColor(Color.TRANSPARENT);

        codeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CountryPicker countryPicker =
                        new CountryPicker.Builder().with(getActivity())
                                .listener(new OnCountryPickerListener() {
                                    @Override public void onSelectCountry(Country country) {
                                        //DO something here
                                        String dialCode = country.getDialCode();
                                        codeButton.setText(dialCode);
                                    }
                                })
                                .build();
                countryPicker.showDialog(getFragmentManager());// Show the dialog

            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msg = editmsg.getText().toString();
                //msg.replaceAll(" ", "%20");
                //msg = Html.fromHtml(msg).toString();
                //msg = TextUtils.htmlEncode(msg);
                url = editno.getText().toString();
                if (url.isEmpty()){
                    Toast.makeText(getActivity(), "Enter Whatsapp Number!", Toast.LENGTH_SHORT).show();
                }
                else{
                    url = "https://api.whatsapp.com/send?phone="+codeButton.getText() + url + "&text=" + msg;
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    try{
                        startActivity(intent);
                        intent.setPackage("com.whatsapp");
                    }
                    catch (Exception e){
                        Toast.makeText(getActivity(), "Whatsapp not installed!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


        return myView;
    }
}