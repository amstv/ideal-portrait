package com.example.navigation;

public enum Navigation {
	FAR(3),
	MEDIUM(2),
	NEAR(1),
	IDEAL(0);

	private int navigationCode;
	
	private Navigation(int navigationCode){
		this.navigationCode = navigationCode;
	}
	
	public int getNavigationCode(){
		return navigationCode;
	}
	
	public static Navigation getNavigationByCode(int code){
		switch(code){
			case 0:
				return IDEAL;
			case 1:
				return NEAR;
			case 2:
				return MEDIUM;
			case 3:
				return FAR;
			default:
				return FAR;
		}
	}
}
