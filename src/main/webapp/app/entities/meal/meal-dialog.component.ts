import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Meal } from './meal.model';
import { MealPopupService } from './meal-popup.service';
import { MealService } from './meal.service';
import { Recipe, RecipeService } from '../recipe';
import { Person, PersonService } from '../person';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-meal-dialog',
    templateUrl: './meal-dialog.component.html'
})
export class MealDialogComponent implements OnInit {

    meal: Meal;
    isSaving: boolean;

    recipes: Recipe[];

    people: Person[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private mealService: MealService,
        private recipeService: RecipeService,
        private personService: PersonService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.recipeService.query()
            .subscribe((res: ResponseWrapper) => { this.recipes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.personService.query()
            .subscribe((res: ResponseWrapper) => { this.people = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.meal.id !== undefined) {
            this.subscribeToSaveResponse(
                this.mealService.update(this.meal));
        } else {
            this.subscribeToSaveResponse(
                this.mealService.create(this.meal));
        }
    }

    private subscribeToSaveResponse(result: Observable<Meal>) {
        result.subscribe((res: Meal) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Meal) {
        this.eventManager.broadcast({ name: 'mealListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackRecipeById(index: number, item: Recipe) {
        return item.id;
    }

    trackPersonById(index: number, item: Person) {
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
    selector: 'jhi-meal-popup',
    template: ''
})
export class MealPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mealPopupService: MealPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.mealPopupService
                    .open(MealDialogComponent as Component, params['id']);
            } else {
                this.mealPopupService
                    .open(MealDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
