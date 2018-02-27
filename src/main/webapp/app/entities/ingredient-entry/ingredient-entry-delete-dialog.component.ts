import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IngredientEntry } from './ingredient-entry.model';
import { IngredientEntryPopupService } from './ingredient-entry-popup.service';
import { IngredientEntryService } from './ingredient-entry.service';

@Component({
    selector: 'jhi-ingredient-entry-delete-dialog',
    templateUrl: './ingredient-entry-delete-dialog.component.html'
})
export class IngredientEntryDeleteDialogComponent {

    ingredientEntry: IngredientEntry;

    constructor(
        private ingredientEntryService: IngredientEntryService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.ingredientEntryService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'ingredientEntryListModification',
                content: 'Deleted an ingredientEntry'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ingredient-entry-delete-popup',
    template: ''
})
export class IngredientEntryDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ingredientEntryPopupService: IngredientEntryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.ingredientEntryPopupService
                .open(IngredientEntryDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
