import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { BlackListComponent } from './black-list.component';
import { BlackListDetailComponent } from './black-list-detail.component';
import { BlackListPopupComponent } from './black-list-dialog.component';
import { BlackListDeletePopupComponent } from './black-list-delete-dialog.component';

export const blackListRoute: Routes = [
    {
        path: 'black-list',
        component: BlackListComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BlackLists'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'black-list/:id',
        component: BlackListDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BlackLists'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const blackListPopupRoute: Routes = [
    {
        path: 'black-list-new',
        component: BlackListPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BlackLists'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'black-list/:id/edit',
        component: BlackListPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BlackLists'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'black-list/:id/delete',
        component: BlackListDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BlackLists'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
