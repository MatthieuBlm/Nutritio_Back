import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NutritioBackSharedModule } from '../../shared';
import {
    GrocerieService,
    GroceriePopupService,
    GrocerieComponent,
    GrocerieDetailComponent,
    GrocerieDialogComponent,
    GroceriePopupComponent,
    GrocerieDeletePopupComponent,
    GrocerieDeleteDialogComponent,
    grocerieRoute,
    groceriePopupRoute,
} from './';

const ENTITY_STATES = [
    ...grocerieRoute,
    ...groceriePopupRoute,
];

@NgModule({
    imports: [
        NutritioBackSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        GrocerieComponent,
        GrocerieDetailComponent,
        GrocerieDialogComponent,
        GrocerieDeleteDialogComponent,
        GroceriePopupComponent,
        GrocerieDeletePopupComponent,
    ],
    entryComponents: [
        GrocerieComponent,
        GrocerieDialogComponent,
        GroceriePopupComponent,
        GrocerieDeleteDialogComponent,
        GrocerieDeletePopupComponent,
    ],
    providers: [
        GrocerieService,
        GroceriePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NutritioBackGrocerieModule {}
