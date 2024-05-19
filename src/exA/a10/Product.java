package exA.a10;

/*
 商品登録のための定型化された生成者、各項目に対応する変数、変数にアクセスするためのゲッター、セッター生成過程とテストのための
 to stringメソッドオーバーライディングを含む構成要素が備えられたクラス
 @Author ju_jeongseok
 */

public class Product extends RegisterProduct{
	private String productCode = null;
	private String productName = null;
	private String price = null;

	public Product(String productCode, String productName, String price) {
		this.productCode = productCode;
		this.productName = productName;
		this.price = price;
	}

	public String getProductCode() {
		return productCode;
	}

	public String getProductName() {
		return productName;
	}

	public String getPrice() {
		return price;
	}

	 @Override
	  public String toString() {
	    return productCode + "," + productName + "," + price;
	  }
}
