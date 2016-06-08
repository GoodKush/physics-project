package com.goodkush.phys;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class Question 
{
	private boolean isMC;
	private String[] s = new String [4];
	private String ques;
	private String ans;
		
	//4 question multiple choice
	Question(String quesText, String a, String b, String c, String d, int answer)
	{
		isMC = true;
		s[0] = a;
		s[1] = b;
		s[2] = c;
		s[3] = d;
		ans = s[answer];
		
		ques = quesText;
		
	}
	//True-false question
	Question(String quesText, int answer)
	{
		isMC = false;
		ques = quesText;
		ans = answer == 0?"True":"False";
	}
	
	public boolean isMC()
	{
		return isMC;
	}
	
	public void setUpQuestion(Label q,TextButton... buttons)
	{
		q.setText(ques);
		
		if(isMC)
		{
			for(int i=0;i<s.length;i++)
			{
				buttons[i].setText(s[i]);
				buttons[i].setVisible(true);
			}
		}
		else
		{
			buttons[0].setText("True");
			buttons[1].setText("False");
			buttons[2].setVisible(false);
			buttons[3].setVisible(false);

		}
	}
	
	//returns if answer is correct or incorrect
	public boolean checkAnswer(String s)
	{
		System.out.println(s);
		System.out.println(ans);
		return s.equals(ans);
	}
	
	
	
}
