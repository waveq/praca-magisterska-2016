using UnityEngine;
using System.Collections;
using UnityEngine.UI;

public class ButtonsHandler : MonoBehaviour {

	public Canvas colorSelectCanvas;


	private GameController gameController;
	private int indexOfNewBall = -1;

	void Start() {
		findGameController ();

	}

	void Update() {
		if (Input.GetKey ("escape")) {
			QuitGame ();
		}
	}

	public void QuitGame() {
		Application.Quit();
	}

	private void findGameController() {
		GameObject gameControllerObject = GameObject.Find ("_GameController");
		if (gameControllerObject == null) {
			Debug.Log ("Cannot find '_GameController' Object");
		} else {
			gameController = gameControllerObject.GetComponent<GameController> ();
		}
	}

	public void SelectIndex(int index) {
		if(gameController.getLost()) {
			return;
		}
		colorSelectCanvas.enabled = true;
		indexOfNewBall = index;
	}

	public void Cancel() {
		colorSelectCanvas.enabled = false;
		indexOfNewBall = -1;
	}

	public void AddBall(int colorNumber) {
		colorSelectCanvas.enabled = false;
		gameController.AddElement (indexOfNewBall, colorNumber);
	}

	public void PlayAgain() {
		Application.LoadLevel ("Scene1");
	}
}
