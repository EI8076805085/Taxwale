package com.ennovation.taxwale.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.ennovation.taxwale.Fragment.ITRAllFileFragment;
import com.ennovation.taxwale.Fragment.ITRReceivedFileFragment;
import com.ennovation.taxwale.Fragment.ITRSendFileFragment;

public class GSTFilePagerAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;
    public GSTFilePagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Fragment tab1 = new ITRAllFileFragment();
                return tab1;
            case 1:
                Fragment tab2 = new ITRSendFileFragment();
                return tab2;
            case 2:
                Fragment tab3 = new ITRReceivedFileFragment();
                return tab3;

            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
