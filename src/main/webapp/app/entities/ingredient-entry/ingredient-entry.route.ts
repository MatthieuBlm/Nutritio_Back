import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { IngredientEntryComponent } from './ingredient-entry.component';
import { IngredientEntryDetailComponent } from './ingredient-entry-detail.component';
import { IngredientEntryPopupComponent } from './ingredient-entry-dialog.component';
import { IngredientEntryDeletePopupComponent } from './ingredient-entry-delete-dialog.component';

@Injectable()
export class IngredientEntryResolvePagingParams implements Resolve<any> {

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

export const ingredientEntryRoute: Routes = [
    {
        path: 'ingredient-entry',
        component: IngredientEntryComponent,
        resolve: {
            'pagingParams': IngredientEntryResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'IngredientEntries'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ingredient-entry/:id',
        component: IngredientEntryDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'IngredientEntries'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ingredientEntryPopupRoute: Routes = [
    {
        path: 'ingredient-entry-new',
        component: IngredientEntryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'IngredientEntries'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ingredient-entry/:id/edit',
        component: IngredientEntryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'IngredientEntries'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ingredient-entry/:id/delete',
        component: IngredientEntryDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'IngredientEntries'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
