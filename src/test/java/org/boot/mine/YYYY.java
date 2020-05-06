package org.boot.mine;

import org.apache.shiro.crypto.hash.Md5Hash;

public class YYYY {
	public static void main(String[] args) {
		System.out.println(""+  new Md5Hash("adam" + "123" + "5").toHex());
	}
}
