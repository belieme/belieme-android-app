package hanyang.ac.kr.belieme.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import hanyang.ac.kr.belieme.activity.fragment.AdminHistoryFragment;
import hanyang.ac.kr.belieme.activity.fragment.AdminStuffListFragment;
import hanyang.ac.kr.belieme.Globals;
import hanyang.ac.kr.belieme.R;
import hanyang.ac.kr.belieme.activity.fragment.UserHistoryFragment;
import hanyang.ac.kr.belieme.dataType.Permission;
import hanyang.ac.kr.belieme.dataType.UserInfo;
import hanyang.ac.kr.belieme.activity.fragment.SettingFragment;
import hanyang.ac.kr.belieme.activity.fragment.UserStuffListFragment;

public class MainActivity extends AppCompatActivity {

    private UserInfo userInfo;

    private enum status_e {STUFF, HISTORY, SETTING};

    private status_e status;

    // FrameLayout에 각 메뉴의 Fragment를 바꿔 줌
    private FragmentManager fragmentManager = getSupportFragmentManager();

    private Fragment stuffListFragment = new UserStuffListFragment();
    private Fragment historyFragment = new UserHistoryFragment();
    private Fragment settingFragment = new SettingFragment();

    private Button changeModeBtn;

    private BottomNavigationView bottomNavigationView;

    private TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getIntent().getBooleanExtra("logout", false)) {
            Intent intent = new Intent(getApplicationContext(), StartActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            return;
        } else if(getIntent().getBooleanExtra("afterLogin", false)) {  //TODO 1달 뒤에 지우자
            if(Globals.userInfo.getPermission() != Permission.USER && Globals.userInfo.getPermission() != Permission.ERROR) {
                new MaterialAlertDialogBuilder(this)
                        .setTitle("인편쓰거라 이 녀석들아")
                        .setMessage("이름 : 이석환\n" +
                                "생년월일 : 1999.11.26.\n" +
                                "입대일자 : 2020.04.07.\n" +
                                "입영부대 : 31사단")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();
            }
        }//TODO 여기까지

        changeModeBtn = findViewById(R.id.main_btn_changeMode);

        bottomNavigationView = findViewById(R.id.main_bottomNavigationView);

        title = findViewById(R.id.main_textView_title);

        Globals.isAdminMode = false;
        if(Globals.userInfo.getPermission() != Permission.USER && Globals.userInfo.getPermission() != Permission.ERROR) {
            changeModeBtn.setVisibility(View.VISIBLE);
        }

        changeModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if(!Globals.isAdminMode) {
                     stuffListFragment = new AdminStuffListFragment();
                     historyFragment = new AdminHistoryFragment();
//                     settingFragment = new AdminSettingFragment();
                     changeModeBtn.setText(R.string.set_user_mode);
                     Globals.isAdminMode = true;
                 }
                 else {
                     stuffListFragment = new UserStuffListFragment();
                     historyFragment = new UserHistoryFragment();
                     settingFragment = new SettingFragment();
                     changeModeBtn.setText(R.string.set_admin_mode);
                     Globals.isAdminMode = false;
                 }
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (status) {
                     case STUFF: {
                         title.setText("물품");
                         transaction.replace(R.id.main_frame, stuffListFragment).commitAllowingStateLoss();
                         break;
                     }
                     case HISTORY: {
                         title.setText("기록");
                         transaction.replace(R.id.main_frame, historyFragment).commitAllowingStateLoss();
                         break;
                     }
                     case SETTING: {
                         title.setText("설정");
                         transaction.replace(R.id.main_frame, settingFragment).commitAllowingStateLoss();
                         break;
                     }
                     default: {
                         Log.d("Error On mainActivity", "unknown status");
                     }
                 }
            }
        });

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_frame, stuffListFragment).commitAllowingStateLoss();
        status = status_e.STUFF;
        title.setText("물품");


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.navigation_userStuffList: {
                        title.setText("물품");
                        transaction.replace(R.id.main_frame, stuffListFragment).commitAllowingStateLoss();
                        status = status_e.STUFF;
                        break;
                    }
                    case R.id.navigation_history: {
                        title.setText("기록");
                        transaction.replace(R.id.main_frame, historyFragment).commitAllowingStateLoss();
                        status = status_e.HISTORY;
                        break;
                    }
                    case R.id.navigation_setting: {
                        title.setText("설정");
                        transaction.replace(R.id.main_frame, settingFragment).commitAllowingStateLoss();
                        status = status_e.SETTING;
                        break;
                    }
                }
                return true;
            }
        });
    }

    public void setChangeModeBtnEnabled(boolean enabled) {
        if(Globals.userInfo.getPermission() != Permission.USER && Globals.userInfo.getPermission() != Permission.ERROR) {
            changeModeBtn.setEnabled(enabled);
        }
    }

    public void setChangeModeBtnVisibility(int visibility) {
        if(Globals.userInfo.getPermission() != Permission.USER && Globals.userInfo.getPermission() != Permission.ERROR) {
            changeModeBtn.setVisibility(visibility);
        }
    }
}
