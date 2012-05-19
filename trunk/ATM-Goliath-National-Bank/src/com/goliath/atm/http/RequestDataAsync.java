package com.goliath.atm.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.goliath.atm.http.json.parser.ParserInterface;

public class RequestDataAsync extends AsyncTask<Void, Void, Boolean> {

	private static final int TIMEOUT_SOCK = 45000;
	private static final int TIMEOUT_CONN = 30000;

	private ParserInterface mPaser;
	private String mUrl;
	private JSONObject mData;
	private RequestListenerInterface mListener;

	public RequestDataAsync(String url, JSONObject sendData,
			ParserInterface parser, RequestListenerInterface requester) {
		mUrl = url;
		mPaser = parser;
		mData = sendData;
		mListener = requester;
	}

	@Override
	protected Boolean doInBackground(Void... arg0) {
		boolean result = false;
		
		try {
			mListener.startRequest();
			
			String str = "?";
			String key;
			String value;
			Iterator iterator = mData.keys();
			while (iterator.hasNext()){
				key = (String) iterator.next();
				value = mData.getString(key);
				
				str += key + "=" + value + "&";
			}
			
			if (str.endsWith("&")) str = str.substring(0, str.length() - 1);
			
			Log.v("test", str);
			
			HttpGet http = new HttpGet(mUrl+str);
			
			//HttpPost httpPost = new HttpPost(mUrl);
			//httpPost.setHeader("Content-Type", "application/json");

			//StringEntity tmp = new StringEntity(mData.toString(), "UTF-8");
			//httpPost.setEntity(tmp);
			
			final HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters,
					TIMEOUT_CONN);
			HttpConnectionParams.setSoTimeout(httpParameters, TIMEOUT_SOCK);
			final HttpClient httpclient = new DefaultHttpClient(httpParameters);
			final HttpResponse response = httpclient.execute(http);

			InputStream is = response.getEntity().getContent();
			
			mPaser.parse(convertStreamToString(is),mListener);
			
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		mListener.onRequestFinished(result);
	}

	private String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {
			e.printStackTrace();

		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return sb.toString().replace("<?xml version=\"1.0\" encoding=\"utf-8\"?><string xmlns=\"http://tempuri.org/\">","").replace("</string>","");

	}

}
