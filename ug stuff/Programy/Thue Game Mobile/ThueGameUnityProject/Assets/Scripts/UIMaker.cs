using UnityEngine;
using System.Collections;
using System.Collections.Generic;
using UnityEngine.UI;

public class UIMaker : MonoBehaviour {

	public GameObject redBall;
	public GameObject blueBall;
	public GameObject greenBall;
	public GameObject ballsPanel;
	public GameObject alertBoxLeft;
	public GameObject alertBoxRight;
	public Text messageText;

	private static readonly int SPACE_BETWEEN_BALLS = 235;
	private static readonly int INIT_SPACE_FROM_LEFT = 195;

	private ButtonsHandler buttonsHandler;

	void Start() {
		findButtonsHandler ();
	}

	private void findButtonsHandler() {
		GameObject buttonsHandlerObject = GameObject.Find ("_ButtonsHandler");
		if (buttonsHandlerObject == null) {
			Debug.Log ("Cannot find '_ButtonsHandler' Object");
		} else {
			buttonsHandler = buttonsHandlerObject.GetComponent<ButtonsHandler> ();
		}
	}

	public void updateBalls(List<int> sequence) {
		clearPanel ();
		spawnBallsAndButtons (sequence);
	}

	private void spawnBallsAndButtons(List<int> sequence) {
		for (int i = 0; i<sequence.Count; i++) {
			GameObject ballToSpawn = GetCorrectBall(sequence[i]);
			GameObject spawnedBall = InstantiateBall(ballToSpawn);
			placeBall(spawnedBall, new Vector3(i*SPACE_BETWEEN_BALLS+INIT_SPACE_FROM_LEFT, 0, 0));
			
			
			GameObject addButton = GetAddButton(spawnedBall);
			SetButtonProperties(addButton, i+1);
			
			AdjustBallsPanelSize(sequence);
		}
	}

	public void ShowRepetition(int index, int size) {
		int halfOfBall = 75;
		RectTransform alertRectLeft = alertBoxLeft.GetComponent<RectTransform> ();
		alertRectLeft.sizeDelta = new Vector2 (size * SPACE_BETWEEN_BALLS, alertRectLeft.rect.height);
		alertRectLeft.localPosition = new Vector3 (index * SPACE_BETWEEN_BALLS + INIT_SPACE_FROM_LEFT - halfOfBall, alertRectLeft.localPosition.y, 0);

		RectTransform alertRectRight = alertBoxRight.GetComponent<RectTransform> ();
		alertRectRight.sizeDelta = new Vector2 (size * SPACE_BETWEEN_BALLS, alertRectRight.rect.height);
		alertRectRight.localPosition = new Vector3 ((index+size) * SPACE_BETWEEN_BALLS + INIT_SPACE_FROM_LEFT - halfOfBall, alertRectRight.localPosition.y, 0);

	}

	public void setMessage(string text) {
		messageText.text = text;
	}

	private void AdjustBallsPanelSize(List<int> sequence) {
		RectTransform panelRect = ballsPanel.GetComponent<RectTransform> ();

		panelRect.sizeDelta = new Vector2(INIT_SPACE_FROM_LEFT + sequence.Count * SPACE_BETWEEN_BALLS, panelRect.rect.height);
	}

	private GameObject GetCorrectBall(int number) {
		if(number == 0) {
			return redBall;
		} 
		if(number == 1) {
			return blueBall;
		} 
		if(number == 2) {
			return greenBall;
		} 

		return null;
	}

	private GameObject InstantiateBall(GameObject ballToSpawn) {
		return (GameObject)Instantiate(ballToSpawn, new Vector3(0, 0, 0), redBall.transform.rotation);
	}

	private void placeBall(GameObject spawnedBall, Vector3 ballPosition) {
		spawnedBall.transform.SetParent(ballsPanel.transform, false);
		spawnedBall.transform.localPosition = ballPosition;
	}

	private void clearPanel() {
		foreach (Transform child in ballsPanel.transform) {
			if(child.name.Contains("(Clone)")) {
				Destroy(child.gameObject);
			}
		}

	}

	private GameObject GetAddButton(GameObject spawnedBall) {
		foreach (Transform child in spawnedBall.transform) {
			if(child.name == "AddButton") {
				return child.gameObject;
			}
		}	
		return null;
	}

	private void SetButtonProperties(GameObject addButton, int index) {
		Button button  = addButton.GetComponent<Button>();
		button.onClick.AddListener(() => { buttonsHandler.SelectIndex(index);  }); 
	}
}
