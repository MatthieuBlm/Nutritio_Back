import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Meal } from './meal.model';
import { MealService } from './meal.service';

@Component({
    selector: 'jhi-meal-detail',
    templateUrl: './meal-detail.component.html'
})
export class MealDetailComponent implements OnInit, OnDestroy {

    meal: Meal;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private mealService: MealService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMeals();
    }

    load(id) {
        this.mealService.find(id).subscribe((meal) => {
            this.meal = meal;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMeals() {
        this.eventSubscriber = this.eventManager.subscribe(
            'mealListModification',
            (response) => this.load(this.meal.id)
        );
    }
}
