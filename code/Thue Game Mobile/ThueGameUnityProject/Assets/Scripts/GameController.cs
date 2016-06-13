using UnityEngine;
using System.Collections;
using System.Collections.Generic;
using UnityEngine.UI;

public class GameController : MonoBehaviour {

	private List<int> sequence;
	private UIMaker uiMaker;
	private int repetitionIndex = 0;
	private int repetitionSize = 0;
	private int currentScore = 0;
	private int highScore = 0;

	private bool lost = false;

	private static readonly string HIGHSCORE_KEY = "hscore";
	void Start () {
		sequence = new List<int>();
		findUIMaker ();
		setupScoreMessage ();
	}


	private void findUIMaker() {
		GameObject uiMakerObject = GameObject.Find ("_UIMaker");
		if (uiMakerObject == null) {
			Debug.Log ("Cannot find '_UIMaker' Object");
		} else {
			uiMaker = uiMakerObject.GetComponent<UIMaker> ();
		}
	}

	public void AddElement(int index, int element) {
		if (lost) {
			return;
		}
		currentScore ++;
		sequence.Insert (index, element);
		Debug.Log ("Added " + element + " on index " + index);
		uiMaker.updateBalls (sequence);
		setHighScore();
		setupScoreMessage ();
		if (FindRepetition () != null) {
			lost = true;
			uiMaker.ShowRepetition(repetitionIndex, repetitionSize);
		}
	}

	private void setupScoreMessage() {
		highScore = PlayerPrefs.GetInt (HIGHSCORE_KEY, 0);
		uiMaker.setMessage("Score: "+currentScore+" High Score: " +highScore);
	}

	private void setHighScore() {
		if (currentScore > highScore) {
			PlayerPrefs.SetInt(HIGHSCORE_KEY, currentScore);
		}
	}

	public List<int> FindRepetition() {
		List<int> repeatedSequence = null;
		int maxSeqSize = sequence.Count;
		int minSeqSize = 1;
		for(int subSeqSize=minSeqSize; subSeqSize<=maxSeqSize; subSeqSize++) {
			repeatedSequence = CompareSubSequences(subSeqSize);
			if(repeatedSequence != null) {
				return repeatedSequence;
			}
		}
		return null;
	}

	public bool getLost() {
		return lost;
	}

	private List<int> CompareSubSequences(int subSeqSize) {
		List<int> left = new List<int>();
		List<int> right = new List<int>();
		int comparesFitInSequence = (sequence.Count-subSeqSize*2) + 1;
		for(int i=0; i<comparesFitInSequence; i++) {
			for(int j =0;j<subSeqSize;j++) {
				left.Add(sequence[i+j]);
				right.Add(sequence[i+j+subSeqSize]);
			}
			if(listsAreEqual(left, right)) {
				repetitionIndex = i;
				repetitionSize = subSeqSize;
				return left;
			}
			left.Clear();
			right.Clear ();
		}
		return null;
	}

	private bool listsAreEqual(List<int> left, List<int> right) {
		for(int i = 0; i< left.Count; i++) {
			if(left[i] != right[i]) {
				return false;
			}
		}
		return true;
	}

}
