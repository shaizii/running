
package com.abing.guava;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.*;



public class myguava{
	

	public static class jimdbget implements Callable<String>{
		String k;
		public String call(){
            String strProValue="hello "+ k +"!";
            System.out.println("this is  get  from  jimdb ... ...");
            return strProValue;
		}
	}
	

	public static void main(String[] args) throws ExecutionException, InterruptedException {

		System.out.println("------------------------------------------");
        
		Cache<String, String>  localcache = CacheBuilder.newBuilder().expireAfterWrite(5000, TimeUnit.SECONDS).maximumWeight(100)
				.weigher(new Weigher<String, String>(){
					public int weigh(String k, String v){
						System.out.println("weigh=" + (k.length() + v.length()));
						return k.length() + v.length();
					}
				})
				.build();
		final String str = "xiaoming";
		jimdbget jimget=new jimdbget();
		jimget.k = str;
		String resultVal = localcache.get(str, jimget);  
		System.out.println("get(" + str +  ")=" + resultVal);
       
        System.out.println("end");
        
    }
}


//一体化的写法
/*
  String resultVal = cache.get(str, new Callable<String>() {
	  public String call() {  
       String strProValue="hello "+str+"!";
       System.out.println("this is  get  from  jimdb ... ...");
       return strProValue;
}
});
 */ 




//检验容量控制
//for(int i=0; i<50; i++){
//cache.put("123456700"+ String.valueOf(i)  , String.valueOf(i));
//System.out.println(cache.asMap());
//}