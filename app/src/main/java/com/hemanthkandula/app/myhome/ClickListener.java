package com.hemanthkandula.app.myhome;

import android.view.View;

/**
 * Created by Siva Subramanian L on 04-01-2017.
 */

public interface ClickListener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);
}
