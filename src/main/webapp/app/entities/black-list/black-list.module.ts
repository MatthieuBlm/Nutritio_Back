import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NutritioBackSharedModule } from '../../shared';
import {
    BlackListService,
    BlackListPopupService,
    BlackListComponent,
    BlackListDetailComponent,
    BlackListDialogComponent,
    BlackListPopupComponent,
    BlackListDeletePopupComponent,
    BlackListDeleteDialogComponent,
    blackListRoute,
    blackListPopupRoute,
} from './';

const ENTITY_STATES = [
    ...blackListRoute,
    ...blackListPopupRoute,
];

@NgModule({
    imports: [
        NutritioBackSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        BlackListComponent,
        BlackListDetailComponent,
        BlackListDialogComponent,
        BlackListDeleteDialogComponent,
        BlackListPopupComponent,
        BlackListDeletePopupComponent,
    ],
    entryComponents: [
        BlackListComponent,
        BlackListDialogComponent,
        BlackListPopupComponent,
        BlackListDeleteDialogComponent,
        BlackListDeletePopupComponent,
    ],
    providers: [
        BlackListService,
        BlackListPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NutritioBackBlackListModule {}
