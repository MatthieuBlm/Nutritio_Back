import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { IngredientComponent } from './ingredient.component';
import { IngredientDetailComponent } from './ingredient-detail.component';
import { IngredientPopupComponent } from './ingredient-dialog.component';
import { IngredientDeletePopupComponent } from './ingredient-delete-dialog.component';

@Injectable()
export class IngredientResolvePagingParams implements Resolve<any> {

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

export const ingredientRoute: Routes = [
    {
        path: 'ingredient',
        component: IngredientComponent,
        resolve: {
            'pagingParams': IngredientResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ingredients'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ingredient/:id',
        component: IngredientDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ingredients'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ingredientPopupRoute: Routes = [
    {
        path: 'ingredient-new',
        component: IngredientPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ingredients'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ingredient/:id/edit',
        component: IngredientPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ingredients'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ingredient/:id/delete',
        component: IngredientDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ingredients'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
