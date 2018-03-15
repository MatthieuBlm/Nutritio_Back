package com.mac.nutritio.domain;

public class ScoredRecipe extends Recipe{

	long score;

	public ScoredRecipe(long score, Recipe r){
		this.setId(r.getId());
		this.setImage(r.getImage());
		this.setIngredientEntries(r.getIngredientEntries());
		this.setMeals(r.getMeals());
		this.setName(r.getName());
		this.score = score;
	}
	
	public long getScore() {
		return score;
	}

	public void setScore(long score) {
		this.score = score;
	}
	
	

}
