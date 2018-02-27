import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NutritioBackSharedModule } from '../../shared';
import {
    GoalService,
    GoalPopupService,
    GoalComponent,
    GoalDetailComponent,
    GoalDialogComponent,
    GoalPopupComponent,
    GoalDeletePopupComponent,
    GoalDeleteDialogComponent,
    goalRoute,
    goalPopupRoute,
    GoalResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...goalRoute,
    ...goalPopupRoute,
];

@NgModule({
    imports: [
        NutritioBackSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        GoalComponent,
        GoalDetailComponent,
        GoalDialogComponent,
        GoalDeleteDialogComponent,
        GoalPopupComponent,
        GoalDeletePopupComponent,
    ],
    entryComponents: [
        GoalComponent,
        GoalDialogComponent,
        GoalPopupComponent,
        GoalDeleteDialogComponent,
        GoalDeletePopupComponent,
    ],
    providers: [
        GoalService,
        GoalPopupService,
        GoalResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NutritioBackGoalModule {}
