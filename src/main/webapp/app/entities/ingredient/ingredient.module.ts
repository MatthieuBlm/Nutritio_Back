import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NutritioBackSharedModule } from '../../shared';
import {
    IngredientService,
    IngredientPopupService,
    IngredientComponent,
    IngredientDetailComponent,
    IngredientDialogComponent,
    IngredientPopupComponent,
    IngredientDeletePopupComponent,
    IngredientDeleteDialogComponent,
    ingredientRoute,
    ingredientPopupRoute,
    IngredientResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...ingredientRoute,
    ...ingredientPopupRoute,
];

@NgModule({
    imports: [
        NutritioBackSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        IngredientComponent,
        IngredientDetailComponent,
        IngredientDialogComponent,
        IngredientDeleteDialogComponent,
        IngredientPopupComponent,
        IngredientDeletePopupComponent,
    ],
    entryComponents: [
        IngredientComponent,
        IngredientDialogComponent,
        IngredientPopupComponent,
        IngredientDeleteDialogComponent,
        IngredientDeletePopupComponent,
    ],
    providers: [
        IngredientService,
        IngredientPopupService,
        IngredientResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NutritioBackIngredientModule {}
