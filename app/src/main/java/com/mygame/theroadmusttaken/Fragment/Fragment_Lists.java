package com.mygame.theroadmusttaken.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.mygame.theroadmusttaken.Data.Record;
import com.mygame.theroadmusttaken.Protocol.CallBack_RecordsProtocol;
import com.mygame.theroadmusttaken.R;
import com.mygame.theroadmusttaken.Data.RecordList;
import com.mygame.theroadmusttaken.SharedPreferences.RecordSP;

public class Fragment_Lists extends Fragment {

    private TextView [] fragmentList_TV_Place;
    private TextView [] fragmentList_TV_Date;
    private MaterialButton [] fragmentList_MB_Location;

    private CallBack_RecordsProtocol callBack_recordsProtocol;

    public void setCallBack_recordsProtocol(CallBack_RecordsProtocol callBack_recordsProtocol) {
        this.callBack_recordsProtocol = callBack_recordsProtocol;
    }

    private static final String SP_KEY_RECORD_LIST = "SP_KEY_RECORD_LIST";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_list, container, false);

        findViews(view);
        //inside init views we need to use "ZOOM" through ActivityRecordPanel

        //****
        //String s = RecordSP.getInstance().getString("1", "NuN");
        //The continue
        String recordListSP = RecordSP.getInstance().getString(SP_KEY_RECORD_LIST, "Nun");
        if(!recordListSP.contains("Nun")){
        RecordList recordList = new Gson().fromJson(recordListSP, RecordList.class);
            updatePlaceView(recordList);
            updateDateView(recordList);

            //inside init views we need to use "ZOOM" through ActivityRecordPanel
            initViews(recordList);
        }
        //Log.w("Check", s);



        return view;
    }

    private void initViews(RecordList recordList) {
        //fragmentList_MB_Location[0].setOnClickListener(v -> clicked(recordList.getRecords().get(0)));
        fragmentList_MB_Location[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recordList.getRecords().size()>0) {
                    clicked(recordList.getRecords().get(0));
                }
            }
        });

        //fragmentList_MB_Location[1].setOnClickListener(v -> clicked(recordList.getRecords().get(1)));
        fragmentList_MB_Location[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recordList.getRecords().size()>1) {
                    clicked(recordList.getRecords().get(1));
                }
            }
        });

        //fragmentList_MB_Location[2].setOnClickListener(v -> clicked(recordList.getRecords().get(2)));
        fragmentList_MB_Location[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recordList.getRecords().size()>2) {
                    clicked(recordList.getRecords().get(2));
                }
            }
        });

        //fragmentList_MB_Location[3].setOnClickListener(v -> clicked(recordList.getRecords().get(3)));
        fragmentList_MB_Location[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recordList.getRecords().size()>3) {
                    clicked(recordList.getRecords().get(3));
                }
            }
        });

        //fragmentList_MB_Location[4].setOnClickListener(v -> clicked(recordList.getRecords().get(4)));
        fragmentList_MB_Location[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recordList.getRecords().size()>4) {
                    clicked(recordList.getRecords().get(4));
                }
            }
        });

        //fragmentList_MB_Location[5].setOnClickListener(v -> clicked(recordList.getRecords().get(5)));
        fragmentList_MB_Location[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recordList.getRecords().size()>5) {
                    clicked(recordList.getRecords().get(5));
                }
            }
        });

        //fragmentList_MB_Location[6].setOnClickListener(v -> clicked(recordList.getRecords().get(6)));
        fragmentList_MB_Location[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recordList.getRecords().size()>6) {
                    clicked(recordList.getRecords().get(6));
                }
            }
        });

        //fragmentList_MB_Location[7].setOnClickListener(v -> clicked(recordList.getRecords().get(7)));
        fragmentList_MB_Location[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recordList.getRecords().size()>7) {
                    clicked(recordList.getRecords().get(7));
                }
            }
        });

        //fragmentList_MB_Location[8].setOnClickListener(v -> clicked(recordList.getRecords().get(8)));
        fragmentList_MB_Location[8].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recordList.getRecords().size()>8) {
                    clicked(recordList.getRecords().get(8));
                }
            }
        });

        //fragmentList_MB_Location[9].setOnClickListener(v -> clicked(recordList.getRecords().get(9)));
        fragmentList_MB_Location[9].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recordList.getRecords().size()>9) {
                    clicked(recordList.getRecords().get(9));
                }
            }
        });
    }

    private void clicked(Record r) {
        if(callBack_recordsProtocol != null){
            callBack_recordsProtocol.recordP(r);
        }
        //int lat = r.getLat();
        //int log = r.getLog();
        //int points = r.getPoints();

        //markerlocation(lat, log, points)
    }

    private void updateDateView(RecordList recordList) {
        for(int r = 0; r < recordList.getRecords().size(); r++){
            fragmentList_TV_Date[r].setText(recordList.getRecords().get(r).getRecordDate().toString());
        }
    }

    private void updatePlaceView(RecordList recordList) {
        for(int r = 0; r < recordList.getRecords().size(); r++){
            fragmentList_TV_Place[r].setText(String.valueOf(recordList.getRecords().get(r).getPoints()));
        }
    }

    private void findViews(View view) {
        fragmentList_TV_Place = new TextView[]{
                view.findViewById(R.id.fragmentList_Place_TV_st),
                view.findViewById(R.id.fragmentList_Place_TV_nd),
                view.findViewById(R.id.fragmentList_Place_TV_rd),
                view.findViewById(R.id.fragmentList_Place_TV_fourth),
                view.findViewById(R.id.fragmentList_Place_TV_fifth),
                view.findViewById(R.id.fragmentList_Place_TV_sixth),
                view.findViewById(R.id.fragmentList_Place_TV_seventh),
                view.findViewById(R.id.fragmentList_Place_TV_eighth),
                view.findViewById(R.id.fragmentList_Place_TV_nineth),
                view.findViewById(R.id.fragmentList_Place_TV_tenth)
        };

        fragmentList_TV_Date = new TextView[]{
                view.findViewById(R.id.fragmentList_Date_TV_st),
                view.findViewById(R.id.fragmentList_Date_TV_nd),
                view.findViewById(R.id.fragmentList_Date_TV_rd),
                view.findViewById(R.id.fragmentList_Date_TV_fourth),
                view.findViewById(R.id.fragmentList_Date_TV_fifth),
                view.findViewById(R.id.fragmentList_Date_TV_sixth),
                view.findViewById(R.id.fragmentList_Date_TV_seventh),
                view.findViewById(R.id.fragmentList_Date_TV_eighth),
                view.findViewById(R.id.fragmentList_Date_TV_nineth),
                view.findViewById(R.id.fragmentList_Date_TV_tenth)
        };

        fragmentList_MB_Location = new MaterialButton[]{
                view.findViewById(R.id.fragmentList_Location_MB_st),
                view.findViewById(R.id.fragmentList_Location_MB_nd),
                view.findViewById(R.id.fragmentList_Location_MB_rd),
                view.findViewById(R.id.fragmentList_Location_MB_fourth),
                view.findViewById(R.id.fragmentList_Location_MB_fifth),
                view.findViewById(R.id.fragmentList_Location_MB_sixth),
                view.findViewById(R.id.fragmentList_Location_MB_seventh),
                view.findViewById(R.id.fragmentList_Location_MB_eighth),
                view.findViewById(R.id.fragmentList_Location_MB_nineth),
                view.findViewById(R.id.fragmentList_Location_MB_tenth)
        };
    }
}
