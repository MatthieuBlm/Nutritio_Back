import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { StockComponent } from './stock.component';
import { StockDetailComponent } from './stock-detail.component';
import { StockPopupComponent } from './stock-dialog.component';
import { StockDeletePopupComponent } from './stock-delete-dialog.component';

export const stockRoute: Routes = [
    {
        path: 'stock',
        component: StockComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Stocks'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'stock/:id',
        component: StockDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Stocks'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const stockPopupRoute: Routes = [
    {
        path: 'stock-new',
        component: StockPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Stocks'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'stock/:id/edit',
        component: StockPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Stocks'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'stock/:id/delete',
        component: StockDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Stocks'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
