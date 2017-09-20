package com.omise.assignment.applications.modules;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import com.elvishew.xlog.XLog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.omise.assignment.BuildConfig;
import com.omise.assignment.CharityService;

@Module
public class ServiceModule {
	
	@Provides
	@Singleton
	CharityService provideVMSerice(Gson gson, OkHttpClient client) {
		Retrofit retorfit = new Retrofit.Builder().baseUrl(BuildConfig.API_END_POINT)
				.addConverterFactory(GsonConverterFactory.create(gson)).client(client).build();
		return retorfit.create(CharityService.class);
	}
	
	@Provides
	@Singleton
	Gson providedGson() {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
		return gson;
	}
	
	@Provides
	@Singleton
	OkHttpClient provideOkHttpClient() {
		HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
			@Override
			public void log(final String message) {
				XLog.d(message);
			}
		});
		loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
		return new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS)
				.addInterceptor(new RequestInterceptor()).addInterceptor(loggingInterceptor).build();
	}
	
	private class RequestInterceptor implements okhttp3.Interceptor {
		
		@Override
		public Response intercept(final Chain chain) throws IOException {
			Request request = chain.request();
			Request newReuest = request.newBuilder().addHeader("x-token", BuildConfig.API_KEY)
					.method(request.method(), request.body()).build();
			return chain.proceed(newReuest);
		}
	}
}
