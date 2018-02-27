import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { GoalComponent } from './goal.component';
import { GoalDetailComponent } from './goal-detail.component';
import { GoalPopupComponent } from './goal-dialog.component';
import { GoalDeletePopupComponent } from './goal-delete-dialog.component';

@Injectable()
export class GoalResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const goalRoute: Routes = [
    {
        path: 'goal',
        component: GoalComponent,
        resolve: {
            'pagingParams': GoalResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Goals'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'goal/:id',
        component: GoalDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Goals'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const goalPopupRoute: Routes = [
    {
        path: 'goal-new',
        component: GoalPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Goals'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'goal/:id/edit',
        component: GoalPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Goals'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'goal/:id/delete',
        component: GoalDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Goals'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
