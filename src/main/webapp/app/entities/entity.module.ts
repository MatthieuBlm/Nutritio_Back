import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { NutritioBackPersonModule } from './person/person.module';
import { NutritioBackGoalModule } from './goal/goal.module';
import { NutritioBackGrocerieModule } from './grocerie/grocerie.module';
import { NutritioBackBlackListModule } from './black-list/black-list.module';
import { NutritioBackIngredientModule } from './ingredient/ingredient.module';
import { NutritioBackStockModule } from './stock/stock.module';
import { NutritioBackRecipeModule } from './recipe/recipe.module';
import { NutritioBackMealModule } from './meal/meal.module';
import { NutritioBackIngredientEntryModule } from './ingredient-entry/ingredient-entry.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        NutritioBackPersonModule,
        NutritioBackGoalModule,
        NutritioBackGrocerieModule,
        NutritioBackBlackListModule,
        NutritioBackIngredientModule,
        NutritioBackStockModule,
        NutritioBackRecipeModule,
        NutritioBackMealModule,
        NutritioBackIngredientEntryModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NutritioBackEntityModule {}
