/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author marti
 */
public enum Color {
	red(1),
	blue(2),
	green(3);
	private int value;
	private static Map map = new HashMap<>();

	private Color(int value) {
		this.value = value;
	}

	static {
		for (Color e : Color.values()) {
			map.put(e.value, e);
		}
	}

	public static Color valueOf(int value) {
		return (Color) map.get(value);
	}

	public int getValue() {
		return value;
	}
}
