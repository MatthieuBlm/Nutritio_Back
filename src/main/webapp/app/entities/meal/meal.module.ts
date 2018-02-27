import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NutritioBackSharedModule } from '../../shared';
import {
    MealService,
    MealPopupService,
    MealComponent,
    MealDetailComponent,
    MealDialogComponent,
    MealPopupComponent,
    MealDeletePopupComponent,
    MealDeleteDialogComponent,
    mealRoute,
    mealPopupRoute,
} from './';

const ENTITY_STATES = [
    ...mealRoute,
    ...mealPopupRoute,
];

@NgModule({
    imports: [
        NutritioBackSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MealComponent,
        MealDetailComponent,
        MealDialogComponent,
        MealDeleteDialogComponent,
        MealPopupComponent,
        MealDeletePopupComponent,
    ],
    entryComponents: [
        MealComponent,
        MealDialogComponent,
        MealPopupComponent,
        MealDeleteDialogComponent,
        MealDeletePopupComponent,
    ],
    providers: [
        MealService,
        MealPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NutritioBackMealModule {}
