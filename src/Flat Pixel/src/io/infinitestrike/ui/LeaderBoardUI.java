package io.infinitestrike.ui;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import io.infinitestrike.core.DataEntry;
import io.infinitestrike.core.Killable;
import io.infinitestrike.core.util.font.FontLoader;
import io.infinitestrike.entity.InputEvent;
import io.infinitestrike.mechanics.leaderboard.LeaderBoard;

public class LeaderBoardUI extends GUIObject implements Killable{

	private LeaderBoard board;
	private Image backgroundImage = null;
	private Color backgroundColor = new Color(0,0,0,150);
	private Color textColor = Color.white;
	private Font textFont = FontLoader.getFallbackFont();
	
	
	
	public LeaderBoardUI(int x, int y, int w, int h) {
		super(x, y, Math.max(w, 350), Math.max(h, 300));
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(GameContainer arg0, StateBasedGame arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPreDraw(GameContainer arg0, StateBasedGame arg1, Graphics arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDraw(GameContainer arg0, StateBasedGame arg1, Graphics arg2) {
		// TODO Auto-generated method stub
		if(this.backgroundImage == null){
			arg2.setColor(backgroundColor);
			arg2.fill(getBounds());
		}else{
			this.backgroundImage.draw(this.getLocation().x, this.getLocation().y, this.getSize().getWidth(), this.getSize().getHeight());
		}
	}

	@Override
	public void onPostDraw(GameContainer arg0, StateBasedGame arg1, Graphics arg2) {
		// TODO Auto-generated method stub
		Font f = this.textFont;
		
		float x =  this.getLocation().x;
		float y =  this.getLocation().y;
		float w =  this.getSize().getWidth();
		float h =  this.getSize().getHeight();
		
		f.drawString(x + 5, y + 5, "Player Name:", this.textColor);
		f.drawString(x + w - f.getWidth("Score:") - 44, y + 5, "Score:",this.textColor);
		
		ArrayList<DataEntry> entry = (ArrayList<DataEntry>) this.board.getData();
		
		int sizeIndex = 35;
		
		entry = DataEntry.asortAlt(DataEntry.convertToArray(entry));
		
		for(int i = 0; i < Math.min(entry.size(), 10); i++){
			if(entry.get(i).getDataEntryString().length() > 13) {
				f.drawString(x + 5,sizeIndex + y + (25 * i), entry.get(i).getDataEntryString().substring(0,13));
			}else {
				f.drawString(x + 5,sizeIndex + y + (25 * i), entry.get(i).getDataEntryString());
			}
			f.drawString(x + w - f.getWidth("Score:") - 44,sizeIndex + y + (25 * i),""+ entry.get(i).getDataEntryInt());
		}
	}

	public final LeaderBoard getBoard() {
		return board;
	}

	public final void setBoard(LeaderBoard board) {
		this.board = board;
	}

	public final Image getBackgroundImage() {
		return backgroundImage;
	}

	public final void setBackgroundImage(Image backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	public final Color getBackgroundColor() {
		return backgroundColor;
	}

	public final void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public final Color getTextColor() {
		return textColor;
	}

	public final void setTextColor(Color textColor) {
		this.textColor = textColor;
	}

	public final Font getTextFont() {
		return textFont;
	}

	public final void setTextFont(Font textFont) {
		this.textFont = textFont;
	}

	@Override
	public void onPreTick(GameContainer arg0, StateBasedGame arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTick(GameContainer arg0, StateBasedGame arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPostTick(GameContainer arg0, StateBasedGame arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onKeyEvent(InputEvent e, int keyCode, char keyChar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMouseEvent(InputEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDestroy(GameContainer arg0, StateBasedGame arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void kill(Object sender) {
		// TODO Auto-generated method stub
		this.board.close();
	}

}
