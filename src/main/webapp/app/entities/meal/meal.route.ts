import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { MealComponent } from './meal.component';
import { MealDetailComponent } from './meal-detail.component';
import { MealPopupComponent } from './meal-dialog.component';
import { MealDeletePopupComponent } from './meal-delete-dialog.component';

export const mealRoute: Routes = [
    {
        path: 'meal',
        component: MealComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Meals'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'meal/:id',
        component: MealDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Meals'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const mealPopupRoute: Routes = [
    {
        path: 'meal-new',
        component: MealPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Meals'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'meal/:id/edit',
        component: MealPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Meals'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'meal/:id/delete',
        component: MealDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Meals'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
