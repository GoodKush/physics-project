package com.goodkush.phys;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveToAligned;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import java.util.Arrays;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Main extends ApplicationAdapter
{
	private Stage stage;
	private SpriteBatch batch;
	private Skin skin;
	private Vector2 [] tile = new Vector2 [20];
	private boolean playerTurn = true;
	public static TextureRegion p1Tex, p2Tex, boardTex;
	private Player p1, p2;
	private Question [] ques = new Question[40];
	private int curQues = 0;
	private Sound eels, esc, win;
	
	//parallel array indicates which tiles have eels and escalators
	private int [] escEels = new int[20];
	
	
		

	
	@Override
	public void create () 
	{
		
		eels = Gdx.audio.newSound(Gdx.files.internal("eelsSound.wav"));
		esc = Gdx.audio.newSound(Gdx.files.internal("escalatorsSound.wav"));
		win = Gdx.audio.newSound(Gdx.files.internal("sweetVictory.wav"));
		
		//assigns the textures to each player and the background
		p1Tex = new TextureRegion(new Texture(Gdx.files.internal("spongenar.png")));
		p2Tex = new TextureRegion(new Texture(Gdx.files.internal("datboi.png")));
		boardTex = new TextureRegion(new Texture(Gdx.files.internal("gameBoard.jpg")));
		
		
		//assigns a tile position to each tile on the game board.
		tile[0] = new Vector2(285,220);
		tile[1] = new Vector2(430,175);
		tile[2] = new Vector2(575,175);
		tile[3] = new Vector2(720,175);
		tile[4] = new Vector2(875,175);
		tile[5] = new Vector2(1065,175);
		tile[6] = new Vector2(1088,280);
		tile[7] = new Vector2(1059,370);
		tile[8] = new Vector2(877,370);
		tile[9] = new Vector2(738,370);
		tile[10] = new Vector2(593,370);
		tile[11] = new Vector2(440,370);
		tile[12] = new Vector2(260,370);
		tile[13] = new Vector2(235,480);
		tile[14] = new Vector2(240,570);
		tile[15] = new Vector2(440,570);
		tile[16] = new Vector2(586,570);
		tile[17] = new Vector2(734,570);
		tile[18] = new Vector2(880,570);
		tile[19] = new Vector2(1040,570);
		
		//fills all tiles with a value of -1 except for the ones that are either an eel or escalator
		Arrays.fill(escEels, -1);
		
		escEels[1] = 10; 
		escEels[5] = 8;
		escEels[6] = 2;
		escEels[11] = 14; 
		escEels[13] = 1; 
		escEels[18] = 3;
		escEels[17] = 0;
		
		
		//instantiates each player object
		p1 = new Player(p1Tex,64,64);
		p2 = new Player(p2Tex,64,64);
		batch = new SpriteBatch();
		stage = new Stage(new ScreenViewport());
		skin = new Skin(Gdx.files.internal("ui/uiskin.json"));;
		Gdx.input.setInputProcessor(stage);
		
		
		//adds players to the stage
		stage.addActor(p1);
		stage.addActor(p2);
		
		
		//p1.addAction(Actions.moveToAligned(tile[19].x, tile[19].y, Align.center, 5f, Interpolation.pow5));
		Window wndQuestion = new Window("Question", skin);
		Label playerLabel = new Label("Player 1's turn", skin);
		Label message = new Label("",skin);
		Table t = new Table();
		t.add(message);
		
		stage.addActor(t);
		t.setFillParent(true);
		
		TextButton b1 = new TextButton("A", skin);
		TextButton b2 = new TextButton("B", skin);
		TextButton b3 = new TextButton("C", skin);
		TextButton b4 = new TextButton("D", skin);
		Label question = new Label("Q",skin);
		
		
		
		//Questions!
		ques[0] = new Question("If a ball of mass 2kg and a bullet of mass 0.2kg are dropped at the same time, which object hits the ground first?", "Ball",
				"Bullet", "They hit the ground at the same time", "Not enough info", 2);
		
		ques[1] = new Question("What is force equal to?", "Mass x acceleration", "Mass / acceleration", "Velocity x time", "Velocity / time", 0);
		
		
		ques[2] = new Question("What is the acceleration due to gravity on Earth?", "4 m/s^2", "6 m/s^2", "9.8 m/s^2", "8.4 m/s^2", 2);
		
		ques[3] = new Question("What two factors affect the frequency of a pendulum?", "Length and mass", "Length and gravitational acceleration", "Force and displacement", "Length and displacement", 1);
		
		ques[4] = new Question("A sound of a fork with frequency of 440 Hz and a frequency of a key make a beat of 3 Hz. What is the freqeuncy of the key?", "437 Hz", "443 Hz", "Either one above", "None of the above", 2);
		
		ques[5] = new Question("A car is constantly honking its horn. As it approaches a person standing in the street, the person hears the horn", "with an increased wavelength", "with a decreased velocity", "in the opposite direction", "with a higher frequency", 3);
		
		ques[6] = new Question("Impulse is", "Force x time", "Force / time", "Mass / acceleration", "Velocity x distance", 1);
		
		ques[7] = new Question("A ball of 4 kg is held at 3 meters above the ground for 10 seconds. What is the work done against gravity?", "12 J", "0 J", "40 J", "30 J", 1);
		
		ques[8] = new Question("As a pendulum swings through its equilibrium:", "The kinetic energy is the greatest.", "The potential energy is the greatest.", "The restoring force is the greatest.", "The acceleration is the greatest.", 0);
		
		ques[9] = new Question("Spongebob pushes Patrick with a force of 5 N. The force Patrick enacts on Spongebob is:", "5 N", "0 N", "10 N", "Not enoupgh info", 0);
		
		ques[10] = new Question("In a perfectly inelastic collision:", "Kinetic energy is conserved", "The objects couple together", "Kinetic energy is increased", "Speed is increased", 1);
		
		ques[11] = new Question("A projectile is launched with a horizontal velocity of 10 m/s, is in the air for 10 s, and travels 50m. What is the horizontal velocity at 10 seconds?", "20 m/s", "5 m/s", "10 m/s", "Not enough info given", 2);
		
		ques[12] = new Question("As the distance between two particles increase, the force between them:", "Increases", "Decreases", "Stays the same", "Depends only on the chargers", 1);
		
		ques[13] = new Question("The fundamental frequency of a wave is 4 Hz. The frequency of the 4th harmonic is:", "16 Hz", "4 Hz", "1 Hz", "12 Hz", 2);
		
		ques[14] = new Question("Velocity increases when frequency increases", "True", "False", "", "", 1);
		
		ques[15] = new Question("The spreading of a wave disturbance beyond the edge of a barrier is", "constructive interference", "dispertion", "destructive interference", "diffraction", 3);
		
		ques[16] = new Question("A system in static equiilbrium will have motion that is", "at a constant, non-zero velocity", "at rest", "incresing in velocity", "decreasing in velocity", 1);
		
		ques[17] = new Question("If one end of a pipe is closed:", "All harmonics are present", "Only even harmonics are present", "Only odd harmonics are present", "No harmonics are present", 2);
		
		ques[18] = new Question("When speed doubles, momentum:", "doubles", "triples", "halves", "stays the same", 0);

		ques[19] = new Question("Equal _____ allows a seesaw to be balanced","acceleration", "electricity", "linear momentrum", "torque", 3);
		
		ques[20] = new Question("The normal force of a ball of mass M is:", "Mg", "M/d","M", "", 1);
		
		ques[21] = new Question("Two negative charges", "attrack", "repel", "neither", "both", 1);
		
		ques[22] = new Question("In a circuit, current:", "splits across junctions", "is equal across junctions", "can't pass through junctions", "None of the above", 0);
		
		ques[23] = new Question("Power in a circuit is equal to:", "Current x Voltage", "Resistance x Voltage", "Voltage/Current", "Resistance/ Curre", 0);
		
		ques[24] = new Question("The speed of sound depends on", "freqeuncy", "wavelength", "all of the above", "the medium through which it travels", 3);
		
		ques[25] = new Question("As a wave speeds up through a barrier it", "bends towards the normal", "bends away from the normal", "goes straight", "stops", 1);
		
		ques[26] = new Question("The speed of sound is","3 x 10^8","3000", "60", "4 x 10^7", 0);
		
		ques[27] = new Question("Potential energy is:", "mg", "mh", "mg/h", "mgh", 3);
		
		ques[28] = new Question("If the acceleration of a ball is constant then its speed is:","constant", "increasing", "decreasing", "zero", 1);
		
		ques[29] = new Question("In projectile motion:", "the horizontal speed is constant", "the vertical speed is constant", "the mass changes", "none of the above", 0);
		
		ques[30] = new Question("If a force pushes a box to the right with a force of 20 N and another force pushes the box to the left with 10 N, what is the net force?", "30 N", "5 N", "10 N", "0 N", 1);
		
		ques[31] = new Question("If an object expereiences a net force of zero, the acceleration is", "increasing", "decreasing", "constant", "none of the above", 2);
		
		ques[32] = new Question("Work is:", "force/distance", "force + distance", "force x accleration","force x distance", 3);
		
		ques[33] = new Question("If F is the frequency of a pendulum, then the period is:", "F", "5F", "1/F", "F/1", 2);
		
		ques[34] = new Question("According to Coulomb's law, as charge increases","force increaes", "force decreases", "nothing changes", "none of the above", 0);
		
		ques[35] = new Question("Voltage equals:", "current x resistance", "current / resistance", "current + resistance", "current - resistance", 0);
		
		ques[36] = new Question("Resistors in a series are:", "multiplied", "added", "added by their reciprocals", "none of the above", 1);
		
		ques[37] = new Question("No matter what type of collison __________ is always conserved.", "speed", "potential energy", "kinetic energy", "momentum", 3);
		
		ques[38] = new Question("In object in uniform circul motion accelerates towards the", "direction of the velocity", "opposite of the velocity", "towards the center", "there is no accelration", 2);
		
		ques[39] = new Question("A box is pushed off the edge of a table. The box", "gains potential energy", "loses potential energy", "loses kinetic energy", "none of the above", 1);
		
		
		
		ClickListener listener = new ClickListener(Buttons.LEFT)
		{
			@Override
			public void clicked(InputEvent event, float x, float y) 
			{
				TextButton b = (TextButton)event.getListenerActor();
				boolean correct = ques[curQues].checkAnswer(b.getText().toString());
				System.out.println(correct);
				if(correct)
				{
					//increments to next question in array
					curQues++;
					curQues %= ques.length;
					ques[curQues].setUpQuestion(question, b1,b2,b3,b4);
					int roll = (int)(Math.random()*6+1); 
					System.out.println(roll);
					
					if(playerTurn)
					{
						p1.curTile += roll;
						//keeps the player from moving out of bounds
						p1.curTile = Math.min(19, p1.curTile);
						Action specialTile = moveBy(0,0);
						Action sound = run(()->{});
						//statement that tests if the player lands on an eel or escalator tile. If so, the specialTile movement is defined.
						if(escEels[p1.curTile] != -1 || p1.curTile == 17)
						{
							specialTile = moveToAligned(tile[escEels[p1.curTile]].x,tile[escEels[p1.curTile]].y, Align.center, 5f, Interpolation.pow5);
							if(escEels[p1.curTile] > p1.curTile)
							{
								message.setText("You rolled a " + roll + "! ESCEEEEELATORS!");
								sound = run(()->{esc.play();});
							}
							else
							{
								message.setText("You rolled a " + roll + "! EEEEEALLLLLLLLS");
								sound = run(()->{eels.play();});
							}
							
							//checks if player lands on the losing eel
							if (p1.curTile == 17)
							{
								message.setText("SPONGEGAR LOSES! DAT BOI WINS!");
								win.play();
								wndQuestion.remove();
								Dialog d = new Dialog("Game Over", skin){
									protected void result(Object object) {
										Gdx.app.exit();
									}
								}
								.button("OK").text("GG").show(stage);
								
							};
								
						}
						else
						{
							message.setText("You rolled a " + roll + "!");
						}
						wndQuestion.setVisible(false);
						//action sequence that moves the player
						p1.addAction(
								sequence(
										moveToAligned(tile[p1.curTile].x, tile[p1.curTile].y, Align.center, 5f, Interpolation.pow5),
										sound,
										specialTile,
										run(()-> wndQuestion.setVisible(true)),
										run(()-> playerLabel.setText("Player 2's turn")),
										run(()-> message.setText(""))));
						
						p1.curTile = escEels[p1.curTile] == -1? p1.curTile:escEels[p1.curTile];
					
						
						
						//win condition
						if (p1.curTile >= 19)
						{
							message.setText("SPONGEGAR WINS!");
							win.play();
							wndQuestion.remove();
							Dialog d = new Dialog("Game Over", skin)
							{
								protected void result(Object object) 
								{
									Gdx.app.exit();
								}
							}
							.button("OK").text("GG").show(stage);
							;
							
						}
						
						
					}
					else
					{
						//everything is the same for player 2
						p2.curTile += roll;
						p2.curTile = Math.min(19, p2.curTile);
						Action specialTile = moveBy(0,0);
						Action sound = run(()->{});
						if(escEels[p2.curTile] != -1 || p2.curTile == 17)
						{
							specialTile = moveToAligned(tile[escEels[p2.curTile]].x,tile[escEels[p2.curTile]].y, Align.center, 5f, Interpolation.pow5);
							if(escEels[p2.curTile] > p2.curTile)
							{
								message.setText("You rolled a " + roll + "! ESCEEEEELATORS!");
								sound = run(()->{esc.play();});
							}
							else
							{
								message.setText("You rolled a " + roll + "! EEEEEALLLLLLLLS");
								sound = run(()->{eels.play();});
							}
							if (p2.curTile == 17)
							{
								message.setText("DAT BOI LOSES! SPONGEGAR WINS!");
								win.play();
								wndQuestion.remove();
								Dialog d = new Dialog("Game Over", skin){
									protected void result(Object object) {
										Gdx.app.exit();
									}
								}
								.button("OK").text("GG").show(stage);
								
							};
						}
						else
						{
							message.setText("You rolled a " + roll + "!");
						}
						
						wndQuestion.setVisible(false);
						p2.addAction(
								sequence(
								moveToAligned(tile[p2.curTile].x, tile[p2.curTile].y, Align.center, 5f, Interpolation.pow5),
								sound,
								specialTile,
								run(()-> wndQuestion.setVisible(true)),
								run(()-> playerLabel.setText("Player 1's turn")),
								run(()-> message.setText(""))));
						
						p2.curTile = escEels[p2.curTile] == -1? p2.curTile:escEels[p2.curTile];
						
						
						if (p2.curTile >= 19)
						{
							message.setText("OH #%*#! DAT BOI WINS!");
							win.play();
							wndQuestion.remove();
							Dialog d = new Dialog("Game Over", skin){
								protected void result(Object object) {
									Gdx.app.exit();
								}
							}
							.button("OK").text("GG").show(stage);
							;
							
						}
						
					}
				}
				else
				{
					if(playerTurn)
						playerLabel.setText("Wrong! Player 2 gets a try!");
					else
						playerLabel.setText("Wrong! Player 1 gets a try!");
					
				}
				
				playerTurn = !playerTurn;
				
			}
			
		}
		;
		
		b1.addListener(listener);
		b2.addListener(listener);
		b3.addListener(listener);
		b4.addListener(listener);
		
		
		wndQuestion.add(question).colspan(4);
		wndQuestion.row().padTop(60);
		wndQuestion.add(b1);
		wndQuestion.add(b2);
		wndQuestion.add(b3);
		wndQuestion.add(b4);
		wndQuestion.row().padTop(60);
		wndQuestion.add(playerLabel).colspan(4);
		wndQuestion.setSize(1000, 700);
		
		stage.addActor(wndQuestion);
		
		ques[0].setUpQuestion(question,b1,b2,b3,b4);

	}
	
	//refreshes the screen
	@Override
	public void render () 
	{
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.getBatch().begin();
		stage.getBatch().draw(boardTex,0, 0, stage.getWidth(), stage.getHeight());
		stage.getBatch().end();
		stage.act();
		stage.draw();
	}
	
	//releases recourses 
	@Override
	public void dispose()
	{
		stage.dispose();	
		p1Tex.getTexture().dispose();
		p2Tex.getTexture().dispose();
		boardTex.getTexture().dispose();
		eels.dispose();
		esc.dispose();
		win.dispose();
	}
}
