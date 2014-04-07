package com.squid.voicesearchwrapper;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;

public class WrapperActivity extends Activity {
	private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234321;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//voice search recognizer intent
	    Intent speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
	    speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
	            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
	    speechIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Voice Search Wrapper");
	    startActivityForResult(speechIntent, VOICE_RECOGNITION_REQUEST_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {
            String str = new String();
            ArrayList<String> list = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
//only using the first result (highest confidence)
                      str += list.get(0);
            Log.d("Recognizer:", "result " + str);

			Intent searchIntent = new Intent(Intent.ACTION_WEB_SEARCH, Uri.parse(""));
//			searchIntent.setFlags(0x10410000);
			searchIntent.setComponent(new ComponentName("<packet name>", "<class name>"));
			searchIntent.setClassName("com.google.android.googlequicksearchbox",
 				"com.google.android.googlequicksearchbox.SearchActivity");

			searchIntent.putExtra("query", str);
//			searchIntent.putExtra("user_query", str);
			startActivity(searchIntent);

			WrapperActivity.this.finish();
	    }
	    else {
	    	WrapperActivity.this.finish();
	    }
	    super.onActivityResult(requestCode, resultCode, data);
	}

}
