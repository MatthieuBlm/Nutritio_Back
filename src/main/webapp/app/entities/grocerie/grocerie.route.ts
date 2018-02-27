import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { GrocerieComponent } from './grocerie.component';
import { GrocerieDetailComponent } from './grocerie-detail.component';
import { GroceriePopupComponent } from './grocerie-dialog.component';
import { GrocerieDeletePopupComponent } from './grocerie-delete-dialog.component';

export const grocerieRoute: Routes = [
    {
        path: 'grocerie',
        component: GrocerieComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Groceries'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'grocerie/:id',
        component: GrocerieDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Groceries'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const groceriePopupRoute: Routes = [
    {
        path: 'grocerie-new',
        component: GroceriePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Groceries'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'grocerie/:id/edit',
        component: GroceriePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Groceries'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'grocerie/:id/delete',
        component: GrocerieDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Groceries'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
