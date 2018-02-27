import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Meal } from './meal.model';
import { MealPopupService } from './meal-popup.service';
import { MealService } from './meal.service';

@Component({
    selector: 'jhi-meal-delete-dialog',
    templateUrl: './meal-delete-dialog.component.html'
})
export class MealDeleteDialogComponent {

    meal: Meal;

    constructor(
        private mealService: MealService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.mealService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'mealListModification',
                content: 'Deleted an meal'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-meal-delete-popup',
    template: ''
})
export class MealDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mealPopupService: MealPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.mealPopupService
                .open(MealDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
