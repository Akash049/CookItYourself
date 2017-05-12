package com.curl.ciykit.curl.curl;

/*
  *Two Proces of the image download
  *  1). To put the image download in separate url and keep adding the images
  *
  *
 */
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.curl.ciykit.curl.AddContent;
import com.curl.ciykit.curl.FCMServices.SharedPrefManager;
import com.curl.ciykit.curl.R;
import com.curl.ciykit.curl.utils.SaveImgFunction;
import com.squareup.picasso.Picasso;
import com.victor.loading.book.BookLoading;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import terranovaproductions.newcomicreader.FloatingActionMenu;

public class CurlActivity extends Activity implements View.OnClickListener,TextToSpeech.OnInitListener
{  protected boolean shouldAskPermissions(){ return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);}
   @TargetApi(23)
    protected void askPermissions() {
        String[] permissions = {
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE"
        };
        int requestCode = 200;
        requestPermissions(permissions, requestCode);
    }
    @Bind(R.id.hider)
    LinearLayout hider;
    @Bind(R.id.size)
    FloatingActionButton TextSize;
    @Bind(R.id.option_menu)
    FloatingActionMenu OptionMenu;
    @Bind(R.id.option)
    FloatingActionButton Options;
    @Bind(R.id.share)
    FloatingActionButton Share;
    @Bind(R.id.loadWeb)
    LinearLayout loadWeb;
    @Bind(R.id.bookmark)
    FloatingActionButton Bookmark;
    @Bind(R.id.speak)
    FloatingActionButton Speak;
    @Bind(R.id.loadvideo)
    VideoView news_video;
    @Bind(R.id.video)
    FrameLayout video;
    @Bind(R.id.play)
    ImageView play;
    @Bind(R.id.speakingAvatar)
    ImageView speaker;


    private int menuAppear = 0;
	private CurlView mCurlView;
    public int TestWidth = 0;
    public int TestHeight = 0;
   // private String VideoURL = "http://www.androidbegin.com/tutorial/AndroidCommercial.3gp";
    public TextToSpeech reader;
	public Bitmap bm_head,bm1,bm2,bm3;
    public Picasso picasso;
    public BookLoading bookLoading;
    int index;
    int playvideo = 0 ;
    int pageCount = 0;
    int working = 0 ;
    int CheckSpeakOut = 0;
    public String mNewsData = null;
    public String DataToRead = null;
    private GestureDetectorCompat mDetector;
    public Uri imageUri;
    public PageProvider pageProvider;
    public int CurrentPageIndex;
    List<String> downloaded;
    Typeface newsHeadFont;
    Typeface newsDataFont;
    Bitmap bm;
    List<Bitmap> newsImg;
    List<Bitmap> listNewsImage;
    ArrayList<Integer> listNewsId;
    ArrayList<String> listNewsHead, listNewsData, listNewsImgUrl, listNewsSource, listNewsCategory, listNewsDate, listNewsUrl , listNewslang,listNewsStatus;

    @Override
    protected void onStop() {
        super.onStop();
        SharedPrefManager.getInstance(getApplicationContext()).saveNews(null);
    }

    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main);
        ButterKnife.bind(this);
        OptionMenu.setMultipleOfFB(3.2f);
        OptionMenu.setIsCircle(true);
        pageProvider = new PageProvider();
        downloaded = new ArrayList<String>();

        reader = new TextToSpeech(this,this);
        reader.setSpeechRate(.85f);
        /*Speak.setOnClickListener(this);
        Share.setOnClickListener(this);
        Bookmark.setOnClickListener(this);*/
        newsDataFont = Typeface.createFromAsset(getAssets(),"font/roboto-light1.ttf");
        newsHeadFont = Typeface.createFromAsset(getAssets(),"font/roboto-regular1.ttf");

        listNewsId = new ArrayList<Integer>();
        listNewsHead = new ArrayList<String>();
        listNewsData = new ArrayList<String>();
        listNewsImgUrl = new ArrayList<String>();
        listNewsSource = new ArrayList<String>();
        listNewsCategory = new ArrayList<String>();
        listNewsDate = new ArrayList<String>();
        listNewsUrl = new ArrayList<String>();
        listNewslang = new ArrayList<String>();
        listNewsStatus = new ArrayList<String>();
        TextSize.setOnClickListener(this);
        bookLoading = (BookLoading)findViewById(R.id.bookloading);
        bookLoading.start();
        index = 0;

        mCurlView = (CurlView) findViewById(R.id.curl);
        newsImg = new ArrayList<Bitmap>();
        listNewsImage = new ArrayList<Bitmap>();
        if (getLastNonConfigurationInstance() != null) {
            index = (Integer) getLastNonConfigurationInstance();
        }

        mNewsData = SharedPrefManager.getInstance(getApplicationContext()).getNews();


        if(mNewsData!=null){
            Toast.makeText(CurlActivity.this,"Recipes Loaded",Toast.LENGTH_SHORT).show();
            PrepareNewsData(mNewsData);
        }else{
            Toast.makeText(CurlActivity.this,"Loading Recipes from DB",Toast.LENGTH_SHORT).show();
            if(isNetworkAvailable(CurlActivity.this))
            {
                Toast.makeText(CurlActivity.this,"Starting Recipe Download",Toast.LENGTH_SHORT).show();
                new AsyncJsonObject().execute("http://ciykit.com/getrecipes.php");
            }
            else{
                Toast.makeText(CurlActivity.this,"Need Internet Connection!",Toast.LENGTH_SHORT).show();
            }
        }
        Speak.setOnClickListener(this);
        speaker.setOnClickListener(this);

	}

    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public void PrepareNewsData(String result)
        {
            try {
                JSONArray array = new JSONArray(result);
                pageCount = array.length();
                int news_id;
                String news_date = null,news_category,news_head,news_data,news_img_url = null,news_source,news_url=null,news_lang=null,news_status=null;
                for(int i = pageCount-1; i>=0 ;i--) {
                    JSONObject jsonObject = array.getJSONObject(i);
                    news_id = Integer.parseInt(jsonObject.optString("news_id"));
                    news_date = jsonObject.optString("updated_at");
                    String date = news_date.substring(0,10) +"   "+news_date.substring(11,16);
                    news_category = jsonObject.optString("news_category");
                    news_data = jsonObject.optString("news_data");
                    news_head = jsonObject.optString("news_head");
                    news_img_url = jsonObject.optString("news_imgurl");
                    news_source = jsonObject.optString("news_source");
                    news_url = jsonObject.optString("news_url");
                    news_lang = jsonObject.optString("lang");
                    news_status = jsonObject.optString("status");

                    listNewsId.add(news_id);
                    listNewsDate.add(date);
                    listNewsHead.add(news_head);
                    listNewsCategory.add(news_category);
                    listNewsImgUrl.add(news_img_url);
                    listNewsSource.add(news_source);
                    listNewsData.add(news_data);
                    listNewsUrl.add(news_url);
                    listNewslang.add(news_lang);
                    listNewsStatus.add(news_status);
                    AddContent object = new AddContent(CurlActivity.this,news_id,news_head,news_data,news_img_url,news_category,news_date,news_source,newsHeadFont,newsDataFont);
                    Bitmap NewsPage = object.getContent();
                    newsImg.add(NewsPage);
                }
                new DownloadSingleImagesTask().execute(listNewsImgUrl.get(0));


                //Toast.makeText(CurlActivity.this,"Data updated in the Database",Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch(id)
        {
            case R.id.size:
                int workIndex = CurrentPageIndex - 1;
                //Toast.makeText(getApplicationContext(),"Increase Size for index : "+workIndex,Toast.LENGTH_SHORT).show();
                AddContent object = new AddContent(CurlActivity.this,listNewsId.get(workIndex),listNewsHead.get(workIndex),listNewsData.get(workIndex),listNewsImage.get(workIndex),listNewsImgUrl.get(workIndex),listNewsCategory.get(workIndex),listNewsDate.get(workIndex),listNewsSource.get(workIndex),newsHeadFont,newsDataFont,10);
                Bitmap NewsPage = object.getContent();
                newsImg.set(workIndex,NewsPage);
                mCurlView.setCurrentIndex(CurrentPageIndex);
                break;
            case R.id.share :
                if (shouldAskPermissions()) {
                    askPermissions();
                }
                 Bitmap bmp = newsImg.get(2);
                 SaveImgFunction obj= new SaveImgFunction(bmp);
                 imageUri = obj.GetUri();
                 Toast.makeText(getApplicationContext(),imageUri.getPath().toString(),Toast.LENGTH_SHORT).show();
                 Intent shareIntent = new Intent();
                 //shareIntent.setAction(Intent.ACTION_SEND);
                 //Target whatsapp:
                 shareIntent.setPackage("com.whatsapp");
                 //shareIntent.setPackage("com.bsb.hike");
                 //Add text and then Image URI
                 shareIntent.putExtra(Intent.EXTRA_TEXT, "Check This News");
                 shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                 shareIntent.setType("image/jpeg");
                 shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                try {
                    startActivity(shareIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(),"WatsApp not installed",Toast.LENGTH_SHORT).show();
                }
                         break;
            case R.id.bookmark :
                int workingIndex = CurrentPageIndex -1;
                String SelectNewsData = listNewsData.get(workingIndex);
                break;
            case R.id.speak :
                if(CheckSpeakOut == 0)
                {
                    int workingIndexSpeak = CurrentPageIndex -1;
                    DataToRead = listNewsData.get(workingIndexSpeak);
                    SpeakOut();
                    CheckSpeakOut = 1;
                }else if(CheckSpeakOut == 1){
                    if (reader != null) {
                        reader.stop();
                        //reader.shutdown();
                    }
                    CheckSpeakOut = 0;
                }
                break;
            case R.id.speakingAvatar :
                Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_SHORT).show();
                if(CheckSpeakOut == 0)
                {
                    int workingIndexSpeak = CurrentPageIndex -1;
                    DataToRead = listNewsData.get(workingIndexSpeak);
                    SpeakOut();
                    CheckSpeakOut = 1;
                }else if(CheckSpeakOut == 1){
                    if (reader != null) {
                        reader.stop();
                        //reader.shutdown();
                    }
                    CheckSpeakOut = 0;
                }
                break;
        }

    }
    @Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (reader != null) {
            reader.stop();
            reader.shutdown();
        }
        super.onDestroy();
    }


    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = reader.setLanguage(new Locale("hi"));

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                Speak.setEnabled(true);
                SpeakOut();
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }

    public void SpeakOut()
    {
        reader.speak(DataToRead,TextToSpeech.QUEUE_FLUSH, null);
    }

    public class DownloadSingleImagesTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected void onPreExecute() {
        }
        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap img = null;
                /*img = download_Image(urls[0]);
                AddContent obj = new AddContent(CurlActivity.this,listNewsId.get(0),listNewsHead.get(0),listNewsData.get(0),img,listNewsImgUrl.get(0),listNewsCategory.get(0),listNewsDate.get(0),listNewsSource.get(0),newsHeadFont);
                Bitmap bm = obj.getContent();
                newsImg.set(0,bm);*/
            return img;
        }
        @Override
        protected void onPostExecute(Bitmap result) {
            String[] urls = listNewsImgUrl.toArray(new String[0]);
            new DownloadImagesTask().execute(urls);
            bookLoading.stop();
            hider.setVisibility(View.INVISIBLE);
            mCurlView.setVisibility(View.VISIBLE);
            downloaded.add(listNewsId.get(0).toString());
            mCurlView = (CurlView) findViewById(R.id.curl);
            mCurlView.setPageProvider(pageProvider);
            mCurlView.setSizeChangedObserver(new SizeChangedObserver());
            mCurlView.setCurrentIndex(index);
            mCurlView.setAllowLastPageCurl(false);
            //mCurlView.setBackgroundResource(R.drawable.end_page);
            mCurlView.setBackgroundColor(getResources().getColor(R.color.white));
        }
        private Bitmap download_Image(String url) {
            Bitmap bm = null;
            try {
                URL aURL = new URL(url);
                URLConnection conn = aURL.openConnection();
                conn.connect();
                InputStream is = conn.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                bm = BitmapFactory.decodeStream(bis);
                bis.close();
                is.close();
            } catch (IOException e) {
                Log.e("Hub","Error getting the image from server : " + e.getMessage().toString());
            }
            return bm;
        }
    }

    public class DownloadImagesTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected void onPreExecute() {
        }
        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap img = null;
            for(int i = 0 ;i < urls.length ; i++ )
            {
                img = download_Image(urls[i]);
                listNewsImage.add(img);
                AddContent obj = new AddContent(CurlActivity.this,listNewsId.get(i),listNewsHead.get(i),listNewsData.get(i),img,listNewsImgUrl.get(i),listNewsCategory.get(i),listNewsDate.get(i),listNewsSource.get(i),newsHeadFont,newsDataFont);
                Bitmap bm = obj.getContent();

                newsImg.set(i,bm);
            }
            return img;
        }
        @Override
        protected void onPostExecute(Bitmap result) {    }
        private Bitmap download_Image(String url) {
            Bitmap bm = null;
            try {
                URL aURL = new URL(url);
                URLConnection conn = aURL.openConnection();
                conn.connect();
                InputStream is = conn.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                bm = BitmapFactory.decodeStream(bis);
                bis.close();
                is.close();
            } catch (IOException e) {
                Log.e("Hub","Error getting the image from server : " + e.getMessage().toString());
            }
            return bm;
        }
    }


    @Override
	public void onPause() {
		super.onPause();
		mCurlView.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
		mCurlView.onResume();
	}

    private Bitmap loadBitmapFromLayout(int width, int height , Bitmap bitmap){
        Bitmap b = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        b.eraseColor(getResources().getColor(R.color.page_background));
        Canvas c = new Canvas(b);
        Drawable d = new BitmapDrawable(getResources(), bitmap);
        int margin = 0;
        int border = 0;
        Rect r = new Rect(margin, margin, width - margin, height - margin);
        int imageWidth = r.width() - (border * 2);
        int imageHeight = imageWidth * d.getIntrinsicHeight()
                / d.getIntrinsicWidth();
        if (imageHeight > r.height() - (border * 2)) {
            imageHeight = r.height() - (border * 2);
            imageWidth = imageHeight * d.getIntrinsicWidth()
                    / d.getIntrinsicHeight();
        }

        r.left += ((r.width() - imageWidth) / 2) - border;
        r.right = r.left + imageWidth + border + border;
        r.top += ((r.height() - imageHeight) / 2) - border;
        r.bottom = r.top + imageHeight + border + border;

        Paint p = new Paint();
        p.setColor(getResources().getColor(R.color.page_background));
        c.drawRect(r, p);
        r.left += border;
        r.right -= border;
        r.top += border;
        r.bottom -= border;
        d.setBounds(r);
        d.draw(c);

        return b;
    }

	@Override
	public Object onRetainNonConfigurationInstance() {
		return mCurlView.getCurrentIndex();
	}

	/*** Bitmap provider */

	private class PageProvider implements CurlView.PageProvider {

		// Bitmap resources.
		private int[] mBitmapIds = { R.drawable.page02 ,R.drawable.end_page};

		@Override
		public int getPageCount() {
			return pageCount+2;
		}

		private Bitmap loadBitmap(int width, int height, int index) {
			Bitmap b = Bitmap.createBitmap(width, height,
					Bitmap.Config.ARGB_8888);
			b.eraseColor(0xFFFFFFFF);
			Canvas c = new Canvas(b);
			Drawable d = getResources().getDrawable(mBitmapIds[index]);

			int margin = 0;
			int border = 0;
			Rect r = new Rect(margin, margin, width - margin, height - margin);

			int imageWidth = r.width() - (border * 2);
			int imageHeight = imageWidth * d.getIntrinsicHeight()
					/ d.getIntrinsicWidth();
			if (imageHeight > r.height() - (border * 2)) {
				imageHeight = r.height() - (border * 2);
				imageWidth = imageHeight * d.getIntrinsicWidth()
						/ d.getIntrinsicHeight();
			}

			r.left += ((r.width() - imageWidth) / 2) - border;
			r.right = r.left + imageWidth + border + border;
			r.top += ((r.height() - imageHeight) / 2) - border;
			r.bottom = r.top + imageHeight + border + border;

			Paint p = new Paint();
			p.setColor(0xFFC0C0C0);
			c.drawRect(r, p);
			r.left += border;
			r.right -= border;
			r.top += border;
			r.bottom -= border;

			d.setBounds(r);
			d.draw(c);

			return b;
		}

		@Override
		public void updatePage(CurlPage page, int width, int height, int index) {

            /*if(index == CurrentPageIndex - 2)
            {
                index = CurrentPageIndex -1;
            }*/
            reader.stop();
            MediaPlayer mp = MediaPlayer.create(CurlActivity.this, R.raw.page_curl);
            mp.start();

            CurrentPageIndex = index;
            TestHeight = height;
            TestWidth = width;

            if(index == pageCount+1){
                //Toast.makeText(CurlActivity.this,"Index : "+index,Toast.LENGTH_SHORT).show();
                Bitmap front = loadBitmap(width, height, 1);
                page.setTexture(front, CurlPage.SIDE_BOTH);
                page.setColor(Color.argb(127, 255, 255, 255),
                        CurlPage.SIDE_BACK);
            }
            else{
                switch (index) {
                    case 0: {
                        Bitmap front = loadBitmap(width, height, 0);
                        page.setTexture(front, CurlPage.SIDE_BOTH);
                        page.setColor(Color.argb(127, 255, 255, 255),
                                CurlPage.SIDE_BACK);
                        break;
                    }
                    default: {
                        try {
                            Bitmap bm = newsImg.get(index-1);
                            working = index - 1;
                            Bitmap front = loadBitmapFromLayout(width, height,bm);
                            page.setTexture(front, CurlPage.SIDE_BOTH);
                            page.setColor(Color.argb(127, 255, 255, 255),
                                    CurlPage.SIDE_BACK);
                            //Toast.makeText(CurlActivity.this,"Index : "+index,Toast.LENGTH_SHORT).show();
                        } catch ( IndexOutOfBoundsException e ) {

                            Bitmap front = loadBitmapFromLayout(width, height,newsImg.get(working));
                            page.setTexture(front, CurlPage.SIDE_BOTH);
                            page.setColor(Color.argb(127, 255, 255, 255),
                                    CurlPage.SIDE_BACK);
                        }
                        break;
                    }
                }
			}
		}

	}

	/**
	 * CurlView size changed observer.
	 */
	private class SizeChangedObserver implements CurlView.SizeChangedObserver {
		@Override
		public void onSizeChanged(int w, int h) {
			if (w > h) {
				mCurlView.setViewMode(CurlView.SHOW_TWO_PAGES);
				mCurlView.setMargins(.1f, .05f, .1f, .05f);
			} else {
				mCurlView.setViewMode(CurlView.SHOW_ONE_PAGE);
				mCurlView.setMargins(.1f, .1f, .1f, .1f);
			}
		}
	}


	/*
	 * Downloading the data
	 *
	 */
    public static String GET(String url){
        InputStream inputStream = null;
        String result = "";
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
            inputStream = httpResponse.getEntity().getContent();
            if(inputStream != null) result = convertInputStreamToString(inputStream);
            else result = "Did not work!";
        } catch (Exception e) {Log.d("InputStream", e.getLocalizedMessage());}
        return result;
    }
    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;
        inputStream.close();
        return result;
    }
    private class AsyncJsonObject extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) { return GET(urls[0]); }
        @Override
        protected void onPostExecute(String result) {
            SharedPrefManager.getInstance(getApplicationContext()).saveNews(result);
            PrepareNewsData(result);
        }
    }

}