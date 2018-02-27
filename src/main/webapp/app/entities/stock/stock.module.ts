import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NutritioBackSharedModule } from '../../shared';
import {
    StockService,
    StockPopupService,
    StockComponent,
    StockDetailComponent,
    StockDialogComponent,
    StockPopupComponent,
    StockDeletePopupComponent,
    StockDeleteDialogComponent,
    stockRoute,
    stockPopupRoute,
} from './';

const ENTITY_STATES = [
    ...stockRoute,
    ...stockPopupRoute,
];

@NgModule({
    imports: [
        NutritioBackSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        StockComponent,
        StockDetailComponent,
        StockDialogComponent,
        StockDeleteDialogComponent,
        StockPopupComponent,
        StockDeletePopupComponent,
    ],
    entryComponents: [
        StockComponent,
        StockDialogComponent,
        StockPopupComponent,
        StockDeleteDialogComponent,
        StockDeletePopupComponent,
    ],
    providers: [
        StockService,
        StockPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NutritioBackStockModule {}
