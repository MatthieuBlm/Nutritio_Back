import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { BlackList } from './black-list.model';
import { BlackListPopupService } from './black-list-popup.service';
import { BlackListService } from './black-list.service';
import { IngredientEntry, IngredientEntryService } from '../ingredient-entry';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-black-list-dialog',
    templateUrl: './black-list-dialog.component.html'
})
export class BlackListDialogComponent implements OnInit {

    blackList: BlackList;
    isSaving: boolean;

    ingrediententries: IngredientEntry[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private blackListService: BlackListService,
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
        if (this.blackList.id !== undefined) {
            this.subscribeToSaveResponse(
                this.blackListService.update(this.blackList));
        } else {
            this.subscribeToSaveResponse(
                this.blackListService.create(this.blackList));
        }
    }

    private subscribeToSaveResponse(result: Observable<BlackList>) {
        result.subscribe((res: BlackList) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: BlackList) {
        this.eventManager.broadcast({ name: 'blackListListModification', content: 'OK'});
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
    selector: 'jhi-black-list-popup',
    template: ''
})
export class BlackListPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private blackListPopupService: BlackListPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.blackListPopupService
                    .open(BlackListDialogComponent as Component, params['id']);
            } else {
                this.blackListPopupService
                    .open(BlackListDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
