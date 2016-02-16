package com.laipai.ossImport;

public class ImportMan {
	
	
	public static void main(String args[]){
		
		new Thread(){
			
			public void run(){
				
				ImportUserImg.importUser();
			}
			
		}.start();
		
		new Thread(){
			
			public void run(){
				
				ImportSubject.importSubject();
			}
			
		}.start();
		
new Thread(){
			
			public void run(){
				
				ImportGalaryImg.importGalaryImg();
			}
			
		}.start();
		
		new Thread(){
			
			public void run(){
				
				ImportGalaryDetail.importGalaryDetail();
			}
			
		}.start();
		
new Thread(){
			
			public void run(){
				
				ImportClubImg.importClub();
			}
			
		}.start();
	}
}
