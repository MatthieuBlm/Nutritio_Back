import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IngredientEntry } from './ingredient-entry.model';
import { IngredientEntryPopupService } from './ingredient-entry-popup.service';
import { IngredientEntryService } from './ingredient-entry.service';
import { Ingredient, IngredientService } from '../ingredient';
import { Stock, StockService } from '../stock';
import { Recipe, RecipeService } from '../recipe';
import { Grocerie, GrocerieService } from '../grocerie';
import { BlackList, BlackListService } from '../black-list';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-ingredient-entry-dialog',
    templateUrl: './ingredient-entry-dialog.component.html'
})
export class IngredientEntryDialogComponent implements OnInit {

    ingredientEntry: IngredientEntry;
    isSaving: boolean;

    ingredients: Ingredient[];

    stocks: Stock[];

    recipes: Recipe[];

    groceries: Grocerie[];

    blacklists: BlackList[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private ingredientEntryService: IngredientEntryService,
        private ingredientService: IngredientService,
        private stockService: StockService,
        private recipeService: RecipeService,
        private grocerieService: GrocerieService,
        private blackListService: BlackListService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.ingredientService.query()
            .subscribe((res: ResponseWrapper) => { this.ingredients = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.stockService.query()
            .subscribe((res: ResponseWrapper) => { this.stocks = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.recipeService.query()
            .subscribe((res: ResponseWrapper) => { this.recipes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.grocerieService.query()
            .subscribe((res: ResponseWrapper) => { this.groceries = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.blackListService.query()
            .subscribe((res: ResponseWrapper) => { this.blacklists = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.ingredientEntry.id !== undefined) {
            this.subscribeToSaveResponse(
                this.ingredientEntryService.update(this.ingredientEntry));
        } else {
            this.subscribeToSaveResponse(
                this.ingredientEntryService.create(this.ingredientEntry));
        }
    }

    private subscribeToSaveResponse(result: Observable<IngredientEntry>) {
        result.subscribe((res: IngredientEntry) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: IngredientEntry) {
        this.eventManager.broadcast({ name: 'ingredientEntryListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackIngredientById(index: number, item: Ingredient) {
        return item.id;
    }

    trackStockById(index: number, item: Stock) {
        return item.id;
    }

    trackRecipeById(index: number, item: Recipe) {
        return item.id;
    }

    trackGrocerieById(index: number, item: Grocerie) {
        return item.id;
    }

    trackBlackListById(index: number, item: BlackList) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-ingredient-entry-popup',
    template: ''
})
export class IngredientEntryPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ingredientEntryPopupService: IngredientEntryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.ingredientEntryPopupService
                    .open(IngredientEntryDialogComponent as Component, params['id']);
            } else {
                this.ingredientEntryPopupService
                    .open(IngredientEntryDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
