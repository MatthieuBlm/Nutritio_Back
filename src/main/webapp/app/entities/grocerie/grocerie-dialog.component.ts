import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Grocerie } from './grocerie.model';
import { GroceriePopupService } from './grocerie-popup.service';
import { GrocerieService } from './grocerie.service';
import { IngredientEntry, IngredientEntryService } from '../ingredient-entry';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-grocerie-dialog',
    templateUrl: './grocerie-dialog.component.html'
})
export class GrocerieDialogComponent implements OnInit {

    grocerie: Grocerie;
    isSaving: boolean;

    ingrediententries: IngredientEntry[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private grocerieService: GrocerieService,
        private ingredientEntryService: IngredientEntryService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.ingredientEntryService.query()
            .subscribe((res: ResponseWrapper) => { this.ingrediententries = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.grocerie.id !== undefined) {
            this.subscribeToSaveResponse(
                this.grocerieService.update(this.grocerie));
        } else {
            this.subscribeToSaveResponse(
                this.grocerieService.create(this.grocerie));
        }
    }

    private subscribeToSaveResponse(result: Observable<Grocerie>) {
        result.subscribe((res: Grocerie) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Grocerie) {
        this.eventManager.broadcast({ name: 'grocerieListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackIngredientEntryById(index: number, item: IngredientEntry) {
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
    selector: 'jhi-grocerie-popup',
    template: ''
})
export class GroceriePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private groceriePopupService: GroceriePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.groceriePopupService
                    .open(GrocerieDialogComponent as Component, params['id']);
            } else {
                this.groceriePopupService
                    .open(GrocerieDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
