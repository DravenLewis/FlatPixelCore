package io.infinitestrike.editor;

import java.util.ArrayList;
import java.util.Collections;

import io.infinitestrike.core.Core;
import io.infinitestrike.core.FPError;
import io.infinitestrike.core.LogBot;
import io.infinitestrike.core.LogBot.Status;

public class LayerManager {
	private ArrayList<Layer> layerList = new ArrayList<Layer>();
	private final Map m;
	private FPError error = new FPError();

	public LayerManager(Map m) {
		this.m = m;
		Core.ArrayListCopy(m.getLayers(), layerList);
	}

	
	public void safelyRemoveIndecies(int[] indices) {
		for(int i = 0; i < indices.length; i++) {
			this.m.getLayers().set(indices[i], null);
		}
		
		ArrayList<Layer> remoteLayerMap = this.m.getLayers();
		for(int i = 0; i < remoteLayerMap.size(); i++) {
			if(remoteLayerMap.get(i) == null) remoteLayerMap.remove(i);
		}
	}
	
	public boolean moveLayerUpSafely(int index) {
		if (moveLayerUp(index)) {
			if (uploadLayers()) {
				return true;
			} else {
				this.error.message = "Cannot Upload Layers";
				return false;
			}
		}
		return false;
	}

	public boolean moveLayerDownSafely(int index) {
		if (moveLayerDown(index)) {
			if (uploadLayers()) {
				return true;
			} else {
				this.error.message = "Cannot Upload Layers";
				return false;
			}
		}
		return false;
	}

	private boolean moveLayerUp(int index) {
		if (downloadLayers()) { // download layers to local copy
			try {
				Collections.swap(layerList, index, index - 1);
				return true;
			} catch (Exception e) {
				LogBot.logDataVerbose(e, Status.ERROR, "Cannot Move Layer Up");
				this.error.message = "AIOOB Error: " + e.getMessage();
				return false;
			}
		}
		this.error.message = "Cannot Download Layers";
		return false;
	}

	private boolean moveLayerDown(int index) {
		if (downloadLayers()) {
			try {
				Collections.swap(layerList, index, index + 1);
				return true;
			} catch (Exception e) {
				LogBot.logDataVerbose(e, Status.ERROR, "Cannot Move Layer Down");
				this.error.message = "AIOOB Error: " + e.getMessage();
				return false;
			}
		}
		this.error.message = "Cannot Download Layers";
		return false;
	}

	private boolean downloadLayers() {
		if (!this.isMapWorkable()) {
			return false;
		}
		
		//Console.Log("[LayerManager] Downloading Layers, clearing local layer list");
		this.layerList.clear();
		Core.ArrayListCopy(this.m.getLayers(), this.layerList);
		return true;
	}

	private boolean uploadLayers() {
		if (!this.isMapWorkable()) {
			return false;
		}
		
		//Console.Log("[LayerManager] Uploading Layers, clearing remote layer list");
		this.m.getLayers().clear();
		Core.ArrayListCopy(this.layerList, this.m.getLayers());
		return true;
	}

	private boolean isMapWorkable() {
		return (!isMapNull() && this.isLayerList());
	}

	public boolean isMapNull() {
		return this.m == null;
	}

	private ArrayList<Layer> getMapLayers() {
		if (!this.isMapNull())
			return null;
		return this.m.getLayers();
	}

	private boolean isLayerList() {
		return this.layerList != null;
	}

	public FPError getError() {
		return this.error;
	}
}
