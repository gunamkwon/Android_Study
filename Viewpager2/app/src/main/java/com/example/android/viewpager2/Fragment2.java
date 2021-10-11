package com.example.android.viewpager2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment2 extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        /*
        * inflate ( resource: Int, root: ViewGroup?, attachToRoot: Boolean)
        * rescource : View를 만들고 싶은 Layout ID
        * root : 생성될 View의 Parent
        * attachToRoot : true로 설정할 경우, root의 자식 view로 자동 추가됩니다.
        * return : attachToRoot가 true일 경우 root가,
        *                         false일 경우 XML내 최상위 뷰가 return됩니다.
        *
        * TIP : 직접 View를 붙여야하는게 아니면 Fasle
        *       보통 FragmentManager에서 자동으로 add() 해준다.
        * */
        View view = inflater.inflate(R.layout.fragment2, container, false);

        return view;
    }
}
