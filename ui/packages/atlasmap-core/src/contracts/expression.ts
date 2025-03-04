/*
    Copyright (C) 2017 Red Hat, Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

            http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/
import { ConfigModel } from '../models/config.model';
import { MappingModel } from '../models/mapping.model';
import { Position } from 'monaco-editor';

export interface IExpressionModel {
  simpleExpression: string;

  generateInitialExpression(): void;
  setConfigModel(cfg: ConfigModel): void;
  toText(): string;
  updateFieldReference(mapping: MappingModel, insertPosition?: Position): void;
}

export interface IExpressionNode {
  uuid: string;
  str: string;
  getUuid(): string;
  toText(): string;
}
