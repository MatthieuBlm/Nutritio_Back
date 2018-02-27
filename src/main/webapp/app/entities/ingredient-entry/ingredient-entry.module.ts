import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NutritioBackSharedModule } from '../../shared';
import {
    IngredientEntryService,
    IngredientEntryPopupService,
    IngredientEntryComponent,
    IngredientEntryDetailComponent,
    IngredientEntryDialogComponent,
    IngredientEntryPopupComponent,
    IngredientEntryDeletePopupComponent,
    IngredientEntryDeleteDialogComponent,
    ingredientEntryRoute,
    ingredientEntryPopupRoute,
    IngredientEntryResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...ingredientEntryRoute,
    ...ingredientEntryPopupRoute,
];

@NgModule({
    imports: [
        NutritioBackSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        IngredientEntryComponent,
        IngredientEntryDetailComponent,
        IngredientEntryDialogComponent,
        IngredientEntryDeleteDialogComponent,
        IngredientEntryPopupComponent,
        IngredientEntryDeletePopupComponent,
    ],
    entryComponents: [
        IngredientEntryComponent,
        IngredientEntryDialogComponent,
        IngredientEntryPopupComponent,
        IngredientEntryDeleteDialogComponent,
        IngredientEntryDeletePopupComponent,
    ],
    providers: [
        IngredientEntryService,
        IngredientEntryPopupService,
        IngredientEntryResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NutritioBackIngredientEntryModule {}
