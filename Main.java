public class Main {

    public static void main(String[] args) {
        // write your code here
    }

    public debutPartie(){//changer cavector<Continent> listecontinent){
	courant_->jouer();
	//idee mais a modifier
	for(int j = 0; j < listecontinent.size(); ++j){
		for(int i = 0; i < listecontinent.at(j).getNbterritoire(); ++i){
			fenetre_.draw(listecontinent.at(j).getlisteterritoire(i).getSprite());
		}
	}
	return true;
}


}