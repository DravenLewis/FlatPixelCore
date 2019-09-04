package io.infinitestrike.level;

import java.util.HashMap;

import io.infinitestrike.entity.EntityManager;

public interface ObjectLoad {
	public void onObjectLoad(int x, int y, int w, int h, HashMap flags, String type, String objdi, EntityManager e);
}
